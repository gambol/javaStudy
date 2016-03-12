package gambol.examples.queue;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * User: zhenbao.zhou
 * Date: 4/8/15
 * Time: 4:20 PM
 */
public class BlockedQueue {
    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingDeque<Integer> basket = new LinkedBlockingDeque<>(3);

        for (int i = 0; i < 4; i++) {
            basket.putFirst(i);
            System.out.println("add queue:" + i);
        }
    }
}
