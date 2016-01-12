package org.gambol.modules.ratelimiter;

import gambol.examples.ratelimiter.RateLimiter;

//import com.google.common.util.concurrent.RateLimiter;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
