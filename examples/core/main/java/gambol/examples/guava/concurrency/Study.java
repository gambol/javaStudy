package gambol.examples.guava.concurrency;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.*;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 学习gauva的同步包中的内容
 *
 * @author kris.zhang
 */
public class Study {

    public void atomic() {
        /** 避免jdk1.7前的菱形表达式 */
        AtomicReference<Object> a = Atomics.newReference();
        AtomicReference<Object> b = Atomics.newReference(null);
        AtomicReferenceArray<Object> c = Atomics.newReferenceArray(new Object[] { null, null });
        AtomicReferenceArray<Object> d = Atomics.newReferenceArray(1);
    }

    public void jdk() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<Object> callable = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return new Object();
            }
        };

        Future<Object> future = executorService.submit(callable);

        try {
            future.get();//阻塞
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        while(!future.isDone()) {} //轮询

        //实现回调
        executorService.submit(new FutureTask<Object>(callable) {

            @Override
            protected void done() {
                //do something
                try {
                    Object o = this.get(); //not blocked
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void guava() {
        /** 包装线程池 */
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        /** 提交工作 */
        ListenableFuture<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
              /*
                 抛出异常，就走到onFailure方法
                 throw new RuntimeException("");
              */
                return "haha";
            }
        });

        /** 上文看到 */
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

        while (future.isDone()) {//忙等
            // 或者 阻塞
            // task.get()
        }

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /** 异常映射 */
        Futures.makeChecked(future, new Function<Exception, NullPointerException>() {
            @Override
            public NullPointerException apply(Exception input) {
                if (input instanceof InterruptedException) {
                    return new NullPointerException();
                }
                return null;
            }
        }).checkedGet();

        /** 我们要支持FutureTask，可以使用该方法 */
        ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });

        /** 我们可以这样 */
        ListenableFuture<?> future1 = service.submit(task);

        /** all tasks submitted should be executed */
        service.shutdown();
    }

    /**
     * Guava包里的Service接口用于封装一个服务对象的运行状态、
     * 包括start和stop等方法。例如web服务器，RPC服务器、计时器等可以实现这个接口。
     * 对此类服务的状态管理并不轻松、需要对服务的开启/关闭进行妥善管理、特别是在多线程环境下尤为复杂。
     * Guava包提供了一些基础类帮助你管理复杂的状态转换逻辑和同步细节。
     */
    //Service.State.NEW
    //Service.State.STARTING
    //Service.State.RUNNING
    //Service.State.STOPPING
    //Service.State.FAILED
    //Service.State.TERMINATED

    public static class MyService1 extends AbstractIdleService {
        @Override
        protected void startUp() throws Exception {

        }

        @Override
        protected void shutDown() throws Exception {

        }

    }

    public static class MyService2 extends AbstractScheduledService {

        @Override
        protected void runOneIteration() throws Exception {

        }

        @Override
        protected Scheduler scheduler() {
            return null;
        }
    }

    public static class MyService3 extends AbstractExecutionThreadService {

        @Override
        protected void run() throws Exception {

        }
    }

    public static class MyService4 extends AbstractService {

        @Override
        protected void doStart() {

        }

        @Override
        protected void doStop() {

        }
    }

    //ServiceManager去管理所有服务
    //上述的状态驱动模型我没仔细研究过，实际工作中目前应该用不到，感兴趣的可以研究一下
}
