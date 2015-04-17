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

    public static void main(String[] argv)
      throws java.io.IOException, java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = "";
        for (int i = 0; i < 1000 ; i++) {
            message = "send message  " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            TimeUnit.SECONDS.sleep(1);
        }

        channel.close();
        connection.close();
    }
}
