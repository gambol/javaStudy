package gambol.examples.ticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

/**
 * Created by zhenbao.zhou on 16/1/11.
 */
public class KVCentralizedTicker extends CentralizedTicker {

    private final static Logger logger = LoggerFactory.getLogger(KVCentralizedTicker.class);

    /**
     * 记录全局通用的key
     */
    String tickerKey;

    KVStorage storage;

    private final static int RETRY_TIMES = 3;

    public KVCentralizedTicker(String tickerKey, KVStorage storage) {
        this.tickerKey = tickerKey;
        this.storage = storage;
    }

    // 写入全局通用的时间戳. 单位是 milliseconds
    public void write() {
        write(System.currentTimeMillis());
    }

    /**
     * 重试三次,如果
     * @param millisecond
     */
    public void write(long millisecond) {
        for (int i = 0; i < RETRY_TIMES; i++) {
            try {
                storage.storeTime(tickerKey, millisecond);
                break;
            } catch (TimeoutException e) {
                logger.error("error in write millisecond. try again", e);
            }
        }
    }

    /**
     * 如果超时, 则返回0.
     * 否则返回
     * @return
     */
    public long read() {
        try {
            return storage.depositeTime(tickerKey);
        } catch (TimeoutException e) {
            logger.warn("error in write millisecond. try again", e);
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("NanoTime:" + System.nanoTime());
            System.out.println("time:" + System.currentTimeMillis());
            Thread.sleep(200);
        }
    }

}
