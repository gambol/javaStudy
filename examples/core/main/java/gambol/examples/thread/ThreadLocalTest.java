package gambol.examples.thread;

/**
 * User: zhenbao.zhou
 * Date: 11/15/14
 * Time: 10:00 PM
 */
public class ThreadLocalTest {

    interface TestClass {
        public int getNextValue();

    }

    static class ThreadLocalOne implements  TestClass{
        private ThreadLocal<Integer> threadLocalMap = new ThreadLocal<Integer>() {
            @Override
            public Integer initialValue() {
                return 0;
            }
        };

        @Override
        public int getNextValue() {
            threadLocalMap.set(threadLocalMap.get() + 1);
            return threadLocalMap.get();
        }
    }

    static class NoThreadLocalOne implements  TestClass{
        int i = 0;

        public int getNextValue() {
            i++;
            return i;
        }
    }

    public static void main(String[] args) {

        ThreadLocalOne tlt = new ThreadLocalOne();

        ThreadRunner threadRunner1 = new ThreadRunner(tlt);
        ThreadRunner threadRunner2 = new ThreadRunner(tlt);
        ThreadRunner threadRunner3 = new ThreadRunner(tlt);

        threadRunner1.start();
        threadRunner2.start();
        threadRunner3.start();

        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        System.out.println("==================");

        NoThreadLocalOne nto = new NoThreadLocalOne();
        threadRunner1 = new ThreadRunner(nto);
        threadRunner2 = new ThreadRunner(nto);
        threadRunner3 = new ThreadRunner(nto);

        threadRunner1.start();
        threadRunner2.start();
        threadRunner3.start();
    }


    static class ThreadRunner extends Thread {

        TestClass tc;

        public ThreadRunner (TestClass t){
            tc = t;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                Integer value = tc.getNextValue();
                System.out.println("threadName:" + this.getName() + "  className: " + tc.getClass().getName() + " value:" +
                  value);
            }
        }
    }

}
