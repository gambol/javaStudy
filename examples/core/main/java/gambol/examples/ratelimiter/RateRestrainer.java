package gambol.examples.ratelimiter;

import com.google.common.util.concurrent.Uninterruptibles;
import gambol.examples.redis.JedisTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhenbao.zhou on 16/1/13.
 */
@ThreadSafe
public abstract class RateRestrainer {

    private final static Logger logger = LoggerFactory.getLogger(RateRestrainer.class);

    private final Object mutex = new Object();

    public static RateRestrainer createRedisRestrainer(double rate, String redisKey, JedisPool jedisPool) {
        return new RedisTokenBucket(rate, redisKey, jedisPool);
    }

    /**
     * 返回现在限制的最大速度
     *
     * @return
     */
    public final double getRate() {
        // TODO 增加实现
        return 0.0;
    }

    /**
     * 获取执行这条记录的permit。 如果获取不到，一直睡眠，直到获取成功
     */
    public double acquire() {
        long stepIntoTime = Ticker.read();
        ReserveResult reserveResult;

        do {
            synchronized (mutex) {
                reserveResult = reserveTicket();
            }
            Ticker.sleep(reserveResult.getMillsToWait());
        } while (reserveResult.continueWait);

        long stepOutTime = Ticker.read();
        return 1.0 * (stepOutTime - stepIntoTime);
    }

    // TODO
    public double tryAcquire() {
        return 0.0;
    }

    // 尝试去获取一个许可。
    abstract ReserveResult reserveTicket();

    private static class RedisTokenBucket extends RateRestrainer {

        /**
         * 最大允许的token数量
         */
        double maxAllowedToken;

        /**
         * 现有的空闲token数量
         */
        double availableToken;

        /**
         * 每秒增加的token数量
         */
        double tokenPerSecond;

        /**
         * jedis的pool
         */
        private JedisPool pool;

        /**
         * jedis template。封装了一些jedis的操作
         */
        private JedisTemplate jedisTemplate;

        /**
         * redis 要用到的key的prefix
         */
        private String redisKey;

        final static String AVAILABLE_TOKEN = "availableToken";
        final static String LAST_REFRESH_TIME = "lastRefreshTime";

        public RedisTokenBucket(double rate, String redisKey, JedisPool jedisPool) {
            tokenPerSecond = rate;
            this.redisKey = redisKey;
            pool = jedisPool;
            maxAllowedToken = rate;
            availableToken = 0;
            jedisTemplate = new JedisTemplate(jedisPool);
            clearRedis();
        }

        @Override
        ReserveResult reserveTicket() {
            final Jedis jedis = pool.getResource();
            jedis.watch(redisKey);

            double availableTokenInRedis = getDoubleFromRedis(AVAILABLE_TOKEN);
            long lastRefreshTimeInRedis = getLongFromRedis(LAST_REFRESH_TIME);
            long timeInRedisMills = fetchRedisTimeMills();

            boolean flag = false; // 运行结果
            try {
                final Transaction transaction = jedis.multi();

                availableToken = Math.min(availableTokenInRedis + (timeInRedisMills - lastRefreshTimeInRedis)
                        * tokenPerSecond / TimeUnit.SECONDS.toMillis(1L), maxAllowedToken);
                transaction.hset(redisKey, LAST_REFRESH_TIME, String.valueOf(timeInRedisMills));

                if (availableToken >= 1) {
                    availableToken--;
                    flag = true;
                }

                transaction.hset(redisKey, AVAILABLE_TOKEN, String.valueOf(availableToken));
                List<Object> resultList = transaction.exec();

                if (CollectionUtils.isEmpty(resultList)) {
                    flag = false; // redis提交失败， 说明别的进程/线程在这次提交之前先运行了。这次申请失败
                }
            } finally {
                pool.returnResource(jedis);
            }

            if (!flag) {
                long sleepFor = Math.round(TimeUnit.SECONDS.toMillis(1L) * (1.0 - availableToken) / tokenPerSecond);
                return ReserveResult.createWaitResult(sleepFor);
            }
            return ReserveResult.createContinueResult();
        }

        /**
         * 清掉redis里的这个key对应的值
         */
        void clearRedis() {
            jedisTemplate.del(redisKey);
        }

        /**
         * 获取redis所在机器的系统时间。 单位是ms
         *
         * @return 时间
         */
        long fetchRedisTimeMills() {
            /**
             * 给默认值
             */
            long timeInRedis = Ticker.read();
            try {
                List<String> timeList = jedisTemplate.execute((Jedis jedis) -> jedis.time());
                timeInRedis = Long.parseLong(timeList.get(0)) * 1000 + Long.parseLong(timeList.get(1)) / 1000;
            } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
                logger.error("error in parse time. use system time", e);
            }
            return timeInRedis;
        }

        /**
         * 从redis 的hash 里 取出相应field的值，转换成double。 如果转换失败（譬如为null，或者这个值不为double），则返回0
         *
         * @return double值
         */
        double getDoubleFromRedis(String field) {
            double ret = 0.0;
            String v = jedisTemplate.hget(redisKey, field);
            try {
                ret = v != null ? Double.parseDouble(v) : 0.0;
            } catch (NumberFormatException | NullPointerException e) {
                logger.warn("error in parseDouble.key:{}, field:{}", redisKey, field, e);
            }
            return ret;
        }

        long getLongFromRedis(String field) {
            long ret = 0;
            String v = jedisTemplate.hget(redisKey, field);
            try {
                ret = v != null ? Long.parseLong(v) : 0;
            } catch (NumberFormatException e) {
                logger.warn("error in parseLong.key:{}, field:{}", redisKey, field, e);
            }

            return ret;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    final static class ReserveResult {
        /**
         * 这次睡眠的时间，单位为 毫秒
         */
        long millsToWait;

        /**
         * 是否需要继续等待
         */
        boolean continueWait;

        /**
         * 预定成功，继续运行
         */
        final static ReserveResult CONTINUE_RESULT = new ReserveResult(0, false);

        /**
         * 创建一个需要等待的对象
         *
         * @param millsToWait
         * @return
         */
        static ReserveResult createWaitResult(long millsToWait) {
            return new ReserveResult(millsToWait, true);
        }

        /**
         * 不需要等待
         *
         * @return
         */
        static ReserveResult createContinueResult() {
            return CONTINUE_RESULT;
        }
    }

    /**
     * 计时器.
     */
    final static class Ticker {

        /**
         * @return 获取当前时间戳。 单位是 ms
         */
        static long read() {
            return System.currentTimeMillis();
        }

        static void sleep(long millsToSleep) {
            if (millsToSleep > 0) {
                Uninterruptibles.sleepUninterruptibly(millsToSleep, TimeUnit.MILLISECONDS);
            }
        }
    }

}
