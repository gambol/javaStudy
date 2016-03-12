package gambol.examples.ratelimiter;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Random;

/**
 * 使用redis所做的limiter
 * Created by zhenbao.zhou on 16/1/12.
 */
public class RedisTokenBucketLimiter {

    private final static Logger logger = LoggerFactory.getLogger(RedisTokenBucketLimiter.class);

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
     * jedis库
     */
    private JedisPool pool;

    public RedisTokenBucketLimiter(double maxAllowedToken, double tokenPerSecond, JedisPool pool) {
        this.maxAllowedToken = maxAllowedToken;
        this.tokenPerSecond = tokenPerSecond;
        this.pool = pool;
    }

    boolean take() {
        final Jedis jedis = pool.getResource();
        jedis.watch("availableToken");
        jedis.watch("lastCalcTime");
        double availableTokenInRedis = 0;
        long lastCalcTimeInRedis = 0;
        try {
            availableTokenInRedis = Double.parseDouble(jedis.get("availableToken"));
            lastCalcTimeInRedis = Long.parseLong(jedis.get("lastCalcTime"));
        } catch (Exception e) {
            logger.error("error in parse token.", e);
        }

        long timeInRedis = System.currentTimeMillis();
        try {
            List<String> timeList = jedis.time();
            timeInRedis = Long.parseLong(timeList.get(0)) * 1000 + Long.parseLong(timeList.get(1)) / 1000;
        } catch (Exception e) {
            logger.error("error in parse time.", e);
        }

        boolean flag = false;

        try {
            final Transaction transaction = jedis.multi();

            availableToken = Math.min(availableTokenInRedis + (timeInRedis - lastCalcTimeInRedis) * tokenPerSecond
                    / 1000.0, maxAllowedToken);
            transaction.set("lastCalcTime", String.valueOf(timeInRedis));

            if (availableToken >= 1) {
                availableToken--;
                flag = true;
            }

            transaction.set("availableToken", String.valueOf(availableToken));

            List<Object> resultList = transaction.exec();
            if (CollectionUtils.isEmpty(resultList)) {
                return false;
            }
            for (Object result : resultList) {
                if (result == null) {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.info("error in transaction.", e);
        } finally{
            pool.returnResource(jedis);
        }

        return flag;
    }

    public static void main(String[] args) throws Exception {
        JedisPool pool = new JedisPool("127.0.0.1");

        RedisTokenBucketLimiter limiter = new RedisTokenBucketLimiter(2, 1, pool);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < 20; j++) {
                    boolean result = limiter.take();
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (Exception e) {

                    }
                    logger.info("runtime :{},  result:{}, threadNum:{}", j, result, Thread.currentThread().getId());
                }

            }).start();
        }

        Thread.sleep(10000);

    }

}
