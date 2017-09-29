package gambol.examples.kafka.gb;

/**
 * Created by zhenbao.zhou on 2017/9/29.
 */
public interface KafkaConstant {

    String bootstrapAddress = "localhost:9092";


    String normalTopic = "p4";

    String partitionedTopicName = "partitioned";

    String filteredTopicName = "filtered";

    String greetingTopicName = "greeting";
}
