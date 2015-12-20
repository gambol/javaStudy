package gambol.examples.mq.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: zhenbao.zhou
 * Date: 4/1/15
 * Time: 12:12 PM
 */
public class Sender {

    private static final String EXCHANGE_NAME = "logs";
    private static String queueName = "queue1";

    public static void main(String[] args) throws Exception {
        //ConnectionParameters param = new ConnectionParameters();
        //param.setPassword("djb2c");
        // param.setUsername("djb2c");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //final Connection connection = factory.newConnection("192.168.236.89", 5672);
        final Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 这里我们声明了direct
         */
        //channel.exchangeDeclare(1, queueName, "direct");
        channel.queueDeclare( queueName, false, false, false, null);

        int counter = 0;
        Random random = new Random();
        System.out.printf("sending");

        while (true) {
            int a = random.nextInt(3);
            if (a == 1) {
                channel.basicPublish("", queueName, null, ("log:" + info + counter++).getBytes());
            } else {
                channel.basicPublish("", queueName, null, ("log:" + warn + counter++).getBytes());
            }
            TimeUnit.MILLISECONDS.sleep(1000);

        channel.close();
        connection.close();
    }
}
