package gambol.examples.kafka.gb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static gambol.examples.kafka.gb.KafkaConstant.filteredTopicName;
import static gambol.examples.kafka.gb.KafkaConstant.greetingTopicName;
import static gambol.examples.kafka.gb.KafkaConstant.normalTopic;
import static gambol.examples.kafka.gb.KafkaConstant.partitionedTopicName;

@Component
public class ProducerApplication {

    public static void main(String[] args) throws Exception {

//        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml", ProducerApplication.class);
        ApplicationContext context = new AnnotationConfigApplicationContext("gambol.examples.kafka.gb");


        MessageProducer producer = context.getBean(MessageProducer.class);


        for (int j = 0; j < 10000 ; j++) {
            producer.sendMessage("Hello, World!" + j);
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }
        }


        /*
         * Sending message to a topic with 5 partition,
         * each message to a different partition. But as per
         * listener configuration, only the messages from
         * partition 0 and 3 will be consumed.
         */
        for (int i = 0; i < 5; i++) {
            producer.sendMessageToPartion("Hello To Partioned Topic!", 0);
        }


        /*
         * Sending message to 'filtered' topic. As per listener
         * configuration,  all messages with char sequence
         * 'World' will be discarded.
         */
        producer.sendMessageToFiltered("Hello Baeldung!");
        producer.sendMessageToFiltered("Hello World!");

//
//        /*
//         * Sending message to 'greeting' topic. This will send
//         * and recieved a java object with the help of
//         * greetingKafkaListenerContainerFactory.
//         */
        producer.sendGreetingMessage(new Greeting("Greetings", "World!"));

       // context.close();
    }

    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }



    public static class MessageProducer {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        @Autowired
        private KafkaTemplate<String, Greeting> greetingKafkaTemplate;


        public void sendMessage(String message) {
            kafkaTemplate.send(normalTopic, message);
        }

        public void sendMessageToPartion(String message, int partition) {
            kafkaTemplate.send(partitionedTopicName, partition, message);
        }

        public void sendMessageToFiltered(String message) {
            kafkaTemplate.send(filteredTopicName, message);
        }

        public void sendGreetingMessage(Greeting greeting) {
            greetingKafkaTemplate.send(greetingTopicName, greeting);
        }
    }
}