package gambol.examples.ratelimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhenbao.zhou on 16/1/12.
 */
public class LeakyBucketLimiter {

    private final  static Logger logger = LoggerFactory.getLogger(LeakyBucketLimiter.class);
    /**
     * 水溜走的速度(相当于允许最大的速率)
     */
    long rate;

    /**
     * 桶子的大小
     */
    long bucketSize;

    /**
     * 上次运行的时间
     */
    long lastCalcTime;

    /**
     * 可以接纳的容量
     */
    long water;

    public LeakyBucketLimiter(long bucketSize, long rate) {
        this.rate = rate;
        this.bucketSize = bucketSize;
    }

    synchronized boolean take() {
        long now = System.currentTimeMillis();
        water = Math.max(water - (now - lastCalcTime) * rate / 1000, 0);
        lastCalcTime = now;

        if (water < bucketSize) {
            water++;
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        LeakyBucketLimiter limiter = new LeakyBucketLimiter(5, 5);

        for(int i = 0; i < 20; i++) {
            boolean result = limiter.take();
            logger.info("runtime :{},  result:{}", i, result);
        }

        Thread.sleep(1000);

        for(int i = 0; i < 20; i++) {
            boolean result = limiter.take();
            logger.info("second time runtime :{},  result:{}", i, result);
        }
    }
}
