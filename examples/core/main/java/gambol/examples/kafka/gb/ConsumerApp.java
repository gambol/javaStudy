package gambol.examples.kafka.gb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


/**
 * Created by zhenbao.zhou on 2017/9/29.
 */
@Component
@Slf4j
public class ConsumerApp {

    public static void main(String[] args) throws Exception {

//        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml", ProducerApplication.class);
        ApplicationContext context = new AnnotationConfigApplicationContext("gambol.examples.kafka.gb");


        MessageListener listener = context.getBean(MessageListener.class);
        try {
            Thread.sleep(100000);
        } catch (Exception e) {

        }
    }


    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }


    public static class MessageListener {

        @KafkaListener(topics = KafkaConstant.normalTopic, containerFactory = "fooKafkaListenerContainerFactory")
        public void listenGroupFoo(String message) {
            try{
               Thread.sleep(1000);
            } catch (Exception e) {
            }

            log.info("Received Messasge in group 'foo': " + message);
        }

//        @KafkaListener(topics = KafkaConstant.normalTopic,  containerFactory = "barKafkaListenerContainerFactory")
//        public void listenGroupBar(String message) {
//            try{
//                Thread.sleep(500);
//            } catch (Exception e) {
//            }
//            log.info("Received Messasge in group 'bar': " + message);
//        }
//
//        @KafkaListener(topics = KafkaConstant.normalTopic, containerFactory = "headersKafkaListenerContainerFactory")
//        public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//            System.out.println("Received Messasge: " + message + " from partition: " + partition);
//
//        }
//
//        @KafkaListener(topicPartitions = @TopicPartition(topic = "partitioned", partitions = { "0", "1" }),  containerFactory="partitionsKafkaListenerContainerFactory")
//        public void listenToPartition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//            System.out.println("Received partition Message: " + message + " from partition: " + partition);
//
//        }
//
//        @KafkaListener(topics = "filtered", containerFactory = "filterKafkaListenerContainerFactory")
//        public void listenWithFilter(String message) {
//            System.out.println("Recieved Message in filtered listener: " + message);
//        }
//
//        @KafkaListener(topics = "greeting", containerFactory = "greetingKafkaListenerContainerFactory")
//        public void greetingListener(Greeting greeting) {
//            System.out.println("Recieved greeting message: " + greeting);
//        }

    }

}
