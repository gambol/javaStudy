package gambol.examples.thread;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by zhenbao.zhou on 16/7/4.
 */
public class ArrayListTest {

    private List<Integer> commonList = Lists.newArrayList();

    static class ThreadA extends Thread {

        private ThreadB threadB;

        public ThreadA(ThreadB threadB) {
            this.threadB = threadB;
        }

        @Override
        public void run() {
            List<Integer> list = threadB.list;
            System.out.println("list address" + list);
            System.out.println("threadB list address" + threadB.list);
            System.out.println("list address" + list);
            for (Integer i : threadB.list) {
                System.out.println("hahah: " + i);
                System.out.println(i+ " threadB list address" + threadB.list);
                System.out.println(i + " list address" + list);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        }
    }

    static class ThreadB extends Thread {

        private List<Integer> list;

        public ThreadB(List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("thread b size:" + list.size());
            } catch (Exception e) {

            }

            //list.add(44);
            list = Lists.newArrayList(100, 200);

            try {
                Thread.sleep(1000);
                System.out.println("thread b size:" + list.size());
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        ArrayListTest t = new ArrayListTest();
        t.commonList = Lists.newArrayList(1, 2, 3,5,6);

        ThreadB threadB = new ThreadB(t.commonList);
        ThreadA threadA = new ThreadA(threadB);

        threadA.start();
        threadB.start();

        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }

    }
}
