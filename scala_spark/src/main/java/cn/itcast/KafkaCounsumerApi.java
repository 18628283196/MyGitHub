package cn.itcast;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class KafkaCounsumerApi {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "node01:9092");
        properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("group.id", "test");
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

    }
}
