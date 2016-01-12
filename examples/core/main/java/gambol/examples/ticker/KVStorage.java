package gambol.examples.ticker;

import java.util.concurrent.TimeoutException;

/**
 * Created by zhenbao.zhou on 16/1/11.
 */
public interface KVStorage {

    /**
     *   用某一个key, 写入时间戳
     */
    void storeTime(String key, long nanoTime) throws TimeoutException;

    /**
     *  读取这个key对应的当时时间戳
     */
    long depositeTime(String key) throws TimeoutException;
}
