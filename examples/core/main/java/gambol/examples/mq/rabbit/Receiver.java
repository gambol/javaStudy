package gambol.examples.mq.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * User: zhenbao.zhou
 * Date: 4/1/15
 * Time: 12:22 PM
 */
public class Receiver {

    /**
     * 两个routeKey
     */
    private static String info = "info";
    private static String warn = "warn";


    public static void main(String[] args) throws Exception {

        //param.setPassword("djb2c");
        //param.setUsername("djb2c");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //final Connection connection = factory.newConnection("192.168.236.89");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        String queueName = "queue1";

        /**
         * 第三个参数：routeKey
         */
        //channel.queueBind(1, queueName, "queue1", warn);

        channel.queueDeclare( queueName, false, false, false, null);

        System.out.println("临时队列名字：" + queueName);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message +"'");

            //TimeUnit.MILLISECONDS.sleep(1000);
            TimeUnit.SECONDS.sleep(1);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }
}