package gambol.examples.ratelimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 *
 * Created by zhenbao.zhou on 16/1/12.
 */
public class TokenBucketLimiter {

    private final  static Logger logger = LoggerFactory.getLogger(TokenBucketLimiter.class);

    /**
     * 最大允许的token数量
     */
    double maxAllowedToken;

    /**
     * 现有的空闲token数量
     */
    double availableToken;

    /**
     * 下一个可以使用token的时间
     */
    long lastCalcTime;

    /**
     * 每秒增加的token数量
     */
    double tokenPerSecond;


    public TokenBucketLimiter(double maxAllowedToken, double tokenPerSecond) {
        this.maxAllowedToken = maxAllowedToken;
        this.tokenPerSecond = tokenPerSecond;
    }


    synchronized boolean take() {
        long now = System.currentTimeMillis();
        availableToken = Math.min(availableToken +  (now - lastCalcTime) * tokenPerSecond / 1000.0, maxAllowedToken);
        lastCalcTime = now;

        if (availableToken >= 1) {
            availableToken --;
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        TokenBucketLimiter limiter = new TokenBucketLimiter(5, 1);
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < 100; j++) {
                    boolean result = limiter.take();
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (Exception e) {

                    }
                    logger.info("runtime :{},  result:{}, threadNum:{}", j, result, Thread.currentThread().getId());
                }

            }).start();
        }
    }

}
