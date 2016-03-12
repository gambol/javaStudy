package org.gambol.modules.ratelimiter;

import gambol.examples.ratelimiter.RateLimiter;

//import com.google.common.util.concurrent.RateLimiter;
import gambol.examples.ratelimiter.RateRestrainer;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhenbao.zhou on 16/1/11.
 */
public class LimiterTest extends TestCase {

    private final static Logger logger = LoggerFactory.getLogger(LimiterTest.class);

    @Test
    public void testOriginalLimiter() throws Exception {
        RateLimiter rateLimiter = RateLimiter.create(2);
        Random random = new Random(100);
        logger.info("Start");
        for (int i = 0; i < 20; i++) {
            rateLimiter.acquire();
            Thread.sleep(random.nextInt(100));
            logger.info("System Nano: " + System.currentTimeMillis() + " i:" + i);
        }
    }

    @Test
    public void testLimiterMultiThread() throws Exception {
        RateLimiter rateLimiter = RateLimiter.create(0.3);
        ExecutorService executors = Executors.newCachedThreadPool();
        int maxThread = 20;
        CountDownLatch countDownLatch = new CountDownLatch(maxThread);
        for (int i = 0; i < maxThread; i++) {

            final int k = i;
            executors.submit(new Runnable() {
                @Override
                public void run() {
                    rateLimiter.acquire();
                    logger.info("System Nano: " + System.currentTimeMillis() + " i:" + k);
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

    }

    @Test
    public void testRedisTokenBucketRestrainer() throws Exception {
        JedisPool jedisPool = new JedisPool("127.0.0.1");
        String key = "lal";
        RateRestrainer limiter = RateRestrainer.createRedisRestrainer(2.0, key, jedisPool);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < 10; j++) {
                    limiter.acquire();
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (Exception e) {

                    }
                    logger.info("runtime :{},   threadNum:{}", j, Thread.currentThread().getId());
                }

            }).start();
        }

        Thread.sleep(100000);
    }

}
