package gambol.examples.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zhenbao.zhou on 16/2/10.
 */
public class ReentrantLockTest {

    private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private Condition condition = lock.newCondition();


    void fooA() {
        try {
            rwLock.readLock().lock();
            System.out.println("foo a go:" + System.currentTimeMillis());
            // condition.await();
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        } finally {
            rwLock.readLock().unlock();
        }
    }


    void fooB() {
        try {
            rwLock.writeLock().lock();
            System.out.println("foo b go:" + System.currentTimeMillis());

            Thread.sleep(2000);
        } catch (InterruptedException e) {

        } finally {
            rwLock.writeLock().unlock();
        }
    }

    static class ThreadA extends Thread {

        private ReentrantLockTest test;

        ThreadA (ReentrantLockTest t) {
            super();
            test = t;
        }

        @Override
        public void run() {
            test.fooA();
        }
    }

    static class ThreadB extends Thread {
        private ReentrantLockTest test;

        ThreadB (ReentrantLockTest t) {
            super();
            test = t;
        }

        @Override
        public void run() {
            test.fooB();
        }
    }

    public static void main(String[] args) throws Exception {
        ReentrantLockTest t = new ReentrantLockTest();
        ThreadA a = new ThreadA(t);
        ThreadB b = new ThreadB(t);

        a.start();
        Thread.sleep(1000);
        b.start();
    }
}
