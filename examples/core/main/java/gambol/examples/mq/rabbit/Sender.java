package gambol.examples.mq.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionParameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: zhenbao.zhou
 * Date: 4/1/15
 * Time: 12:12 PM
 */
public class Sender {

    /**
     * 两个routeKey
     */
    private static String info = "info";
    private static String warn = "warn";


    public static void main(String[] args) throws Exception {
        ConnectionParameters param = new ConnectionParameters();
        param.setPassword("djb2c");
        param.setUsername("djb2c");
        ConnectionFactory factory = new ConnectionFactory(param);
        final Connection connection = factory.newConnection("192.168.236.89", 5672);
        Channel channel = connection.createChannel();
        /**
         * 这里我们声明了direct
         */
        channel.exchangeDeclare(1, "logs", "direct");

        int counter = 0;
        Random random = new Random();

        while (true) {
            int a = random.nextInt(2);
            if (a == 0) {
                channel.basicPublish(1,"logs", info, null, ("log:" + info + counter++).getBytes());
            } else {
                channel.basicPublish(1, "logs", warn, null, ("log:" + warn + counter++).getBytes());
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
