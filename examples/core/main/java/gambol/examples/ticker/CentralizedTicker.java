package gambol.examples.ticker;

import com.google.common.annotations.Beta;
import com.google.common.base.Ticker;

/**
 * 基于guava做的一个ticker, 可以在全局范围内获取当前的时间 Created by zhenbao.zhou on 16/1/11.
 */
@Beta
public abstract class CentralizedTicker extends Ticker {

    /**
     * 写入
     */
    public abstract void write();
}
