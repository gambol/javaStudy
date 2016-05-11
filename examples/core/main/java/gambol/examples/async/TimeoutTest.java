package gambol.examples.async;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhenbao.zhou on 16/3/8.
 */
@Slf4j
public class TimeoutTest {

    public void runTest() {

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        int sleepSecond = 100;

        List<ListenableFuture<Integer>> futureList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            futureList.add(executorService.submit(new SleepAndRandomTask(sleepSecond)));

        }

        for (Future future : futureList) {
            try {
                log.info("start get.");
                Integer random = (Integer)future.get(1, TimeUnit.SECONDS);
                log.info("get ok. random:" + random);
            } catch (TimeoutException e) {
                log.warn("timeout for get. {}", e, 1);
            } catch (Exception e) {
                log.warn("exception get. {}", e, 1);
            }
        }

    }

    public static void main(String[] args) {
        new TimeoutTest().runTest();
    }
}

class SleepAndRandomTask implements Callable<Integer> {

    Random random = new Random();

    int sleepSecond;

    public SleepAndRandomTask(int sleepSecond) {
        this.sleepSecond = sleepSecond;
    }

    @Override
    public Integer call() throws Exception {
        Uninterruptibles.sleepUninterruptibly(sleepSecond, TimeUnit.SECONDS);
        return random.nextInt(200);
    }

}