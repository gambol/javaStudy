package gambol.examples.mq.rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;

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
        ConnectionParameters param = new ConnectionParameters();
        param.setPassword("djb2c");
        param.setUsername("djb2c");
        ConnectionFactory factory = new ConnectionFactory(param);
        final Connection connection = factory.newConnection("192.168.236.89");
        final Channel channel = connection.createChannel();

        String queueName = channel.queueDeclare(1).getQueue();

        /**
         * 第三个参数：routeKey
         */
        channel.queueBind(1, queueName, "logs", warn);

        System.out.println("临时队列名字：" + queueName);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(1, queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message +"'");
        }

    }
}
