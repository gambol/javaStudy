package gambol.examples.thread;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhenbao.zhou on 15/5/1.
 */
public class ThreadStopExample {

    private volatile static boolean stop = false;  // 在我的机器(imac + jdk8)上，是否加volatile不影响整个进程的正常退出。
    // 据说，如果没有volatile时，有可能造成while循环无法退出

    public static void main(String[] args) throws InterruptedException {
        Thread workThread = new Thread() {

            @Override
            public void run() {
                int i = 0;
                while (true) {
                    if (!stop) {
                        i++;
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            System.out.printf("error:," + e.getMessage());
                        }
                        System.out.printf("i:" + i);
                    } else {
                        break;
                    }
                }
            }
        };

        workThread.start();
        TimeUnit.SECONDS.sleep(3);
        stop = true;
    }
}
