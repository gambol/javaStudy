package gambol.examples.ratelimiter;

import com.google.common.util.concurrent.Uninterruptibles;
import gambol.examples.redis.JedisTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhenbao.zhou on 16/1/12.
 */
public abstract class RateRestrainer {

    private final static Logger logger = LoggerFactory.getLogger(RateRestrainer.class);


    /**
     * 返回现在限制的最大速度
     *
     * @return
     */
    public final double getRate() {
        return 0.0;
    }

    /**
     * 获取执行这条记录的permit。
     * 如果获取不到，一直睡眠，直到获取成功
     */
    public double acquire() {

        long stepIntoTime = Ticker.read();
        ReserveResult reserveResult;

        do {
            reserveResult = ReserveResult.INIT;
            Ticker.sleep(reserveResult.getMillsToWait());
        } while (reserveResult.continueWait);

        long stepOutTime = Ticker.read();
        return 1.0 * (stepOutTime - stepIntoTime);
    }

    public double tryAcquire() {
        return 0.0;
    }

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

        public RedisTokenBucket (double rate, String redisKey, JedisPool jedisPool) {
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

            double availableTokenInRedis = 0;
            long lastRefreshTimeInRedis = 0;
            try {
                availableTokenInRedis = Double.parseDouble(jedisTemplate.hget(redisKey, AVAILABLE_TOKEN));
                lastRefreshTimeInRedis = Long.parseLong(jedisTemplate.hget(redisKey, LAST_REFRESH_TIME));
            } catch (Exception e) {
                logger.error("error in parse token.", e);
            }


        }

        /**
         * 清掉redis里的这个key对应的值
         */
        void clearRedis() {
            jedisTemplate.del(redisKey);
        }

        /**
         * 获取redis所在机器的系统时间。 单位是ms
         * @return 时间
         */
        long fetchRedisTimeMills () {
            /**
             * 给默认值
             */
            long timeInRedis = Ticker.read();
            try {
                List<String> timeList = jedisTemplate.execute((Jedis jedis) -> jedis.time());
                timeInRedis = Long.parseLong(timeList.get(0)) * 1000 + Long.parseLong(timeList.get(1)) / 1000;
            } catch (Exception e) {
                logger.error("error in parse time. use system time", e);
            }
            return timeInRedis;
        }
    }



    @Getter
    @Setter
    @AllArgsConstructor
    static class ReserveResult {
        /**
         * 这次睡眠的时间，单位为 毫秒
         */
        long millsToWait;

        /**
         * 是否需要继续等待
         */
        boolean continueWait;

        static ReserveResult INIT = new ReserveResult(0, true);
    }

    static class Ticker {

        static long read() {return System.currentTimeMillis();}

        static void sleep(long millsToSleep) {
            if (millsToSleep > 0) {
                Uninterruptibles.sleepUninterruptibly(millsToSleep, TimeUnit.MILLISECONDS);
            }
        }
    }


}
