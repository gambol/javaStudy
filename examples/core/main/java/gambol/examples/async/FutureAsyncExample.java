package gambol.examples.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhenbao.zhou on 16/2/19.
 */

public class FutureAsyncExample implements Callable {
    private int num;

    public FutureAsyncExample(int aInt) {
        this.num = aInt;
    }

    public String call() throws Exception {
        boolean resultOk = false;
        if (num == 0) {
            resultOk = true;
        } else if (num == 1) {
            while (true) { //infinite loop
                System.out.println("looping....");
                Thread.sleep(3000);
            }
        } else {
            throw new Exception("Callable terminated with Exception!");
        }
        if (resultOk) {
            return "Task done.";
        } else {
            return "Task failed";
        }
    }

    public static void main(String[] args) {
        //定义几个任务
        FutureAsyncExample call1 = new FutureAsyncExample(0);
        FutureAsyncExample call2 = new FutureAsyncExample(1);
        FutureAsyncExample call3 = new FutureAsyncExample(2);

        //初始任务执行工具。
        ExecutorService es = Executors.newFixedThreadPool(3);
        //执行任务，任务启动时返回了一个Future对象，
        Future future1 = es.submit(call1);
        Future future2 = es.submit(call2);
        Future future3 = es.submit(call3);
        try {
            //任务1正常执行完毕，future1.get()会返回线程的值
            System.out.println(future1.get());
            //任务2进行一个死循环，调用future2.cancel(true)来中止此线程。
            Thread.sleep(3000);
            System.out.println("Thread 2 terminated? :" + future2.cancel(true));
            //任务3抛出异常，调用future3.get()时会引起异常的抛出
            System.out.println(future3.get());
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}