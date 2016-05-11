package gambol.examples.guava.cache;

import com.google.common.base.Ticker;
import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author kris.zhang
 */
public class Study {

    public static class Bean {

    }

    private static class MyLoader extends CacheLoader<Integer,Bean> {
        @Override
        public Bean load(Integer key) throws Exception {
            //return beanDao.select(key);

            return new Bean();
        }
    }

    private static final LoadingCache<Integer,Bean> cache =
            CacheBuilder.newBuilder()
                    .ticker(Ticker.systemTicker()) //可用于测试
                    .maximumSize(1) //最大值
                    .initialCapacity(32) //指定初始容量，如果可预期，那么可以减少创建开销
                        //失效策略
                        .expireAfterAccess(1,TimeUnit.DAYS)//访问后1天就失效，如果还有访问时间会被覆盖
                        .expireAfterWrite(1, TimeUnit.DAYS)//写完后，不在写入，那么失效期为1天
//                        //替换策略
//                        .weigher(new Weigher<Integer, Bean>() { //根据kv 设置权重，用于替换策略默认是LRU策略
//                            @Override
//                            public int weigh(Integer key, Bean value) {
//                                return 0;
//                            }
//                        }).maximumWeight(12) //需要指定总权重
                    .removalListener(new RemovalListener<Integer, Bean>() {
                        @Override
                        public void onRemoval(RemovalNotification<Integer, Bean> notification) {
                            /** 当k被回收的时候所调用的移除监听器 */
                            notification.getKey();
                            notification.getValue();
                            notification.getCause();
                        }
                    })
                    .recordStats() //开启统计功能 可以使用Cache.stats()方法获得CacheStats对象
            .build(new MyLoader());


    public static void main(String[] args) {
        /**
         * cache我用的比较多，在好多场景下都用了，比如宝马租车和抢单中心的历史抢单记录。
         * 除了guava中的对象池，我们还有很多可以替代的本地进程内缓存：
         *  （1） EHCache hibernate用的
         *   (2) JCS apache cache system 分布式
         *   (3) OSCache 支持集群，支持持久化，比较强大，应用广泛
         *   (4) JSR-107 java cache sp
         *   ....
         *   为啥用guava缓存。更简单，你不需要导入其他的三方包了。能够解决一定场景下的问题。
         *   我们通常不需要复杂的类库
         */

        //cache.getUnchecked(1);//直接出错



        try {
            cache.get(1);
        } catch (ExecutionException e) {
            //记录日志
            //调用实时接口
            e.printStackTrace();
        }

        cache.getIfPresent(1);//or null

        cache.invalidateAll(); //失效所有

        cache.invalidate(1); //失效单值

        cache.put(1,new Bean()); //加入到缓存

        CacheStats cacheStats = cache.stats();//得到统计数据

        cacheStats.loadCount();
        cacheStats.hitCount();

        cache.refresh(1);//刷新key

        cache.cleanUp();//缓存清理。guava把auto cleaner交换到用户手上

        /**
         * 清理什么时候发生？
         使用CacheBuilder构建的缓存不会"自动"执行清理和回收工作，也不会在某个缓存项过期后马上清理，
         也没有诸如此类的清理机制。相反，它会在写操作时顺带做少量的维护工作，或者偶尔在读操作时做——如果写操作实在太少的话。
         这样做的原因在于：如果要自动地持续清理缓存，就必须有一个线程，这个线程会和用户操作竞争共享锁。
         此外，某些环境下线程创建可能受限制，这样CacheBuilder就不可用了。相反，我们把选择权交到你手里。
         如果你的缓存是高吞吐的，那就无需担心缓存的维护和清理等工作。如果你的 缓存只会偶尔有写操作，而你又不想清理工作阻碍了读操作，
         那么可以创建自己的维护线程，以固定的时间间隔调用Cache.cleanUp()。ScheduledExecutorService可以帮助你很好地实现这样的定时调度。
         */
        /** http://ifeve.com/google-guava-cachesexplained/ */
    }
}
