import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class TestConsumer {
    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        //指定kafka集群地址
        properties.put("bootstrap.servers","192.168.25.111:9092");
        //指定消费者组
        properties.put("group.id","test");
        //key序列化
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        //value的序列化
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        //通过配置创建消费者
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        //订阅消费者
        consumer.subscribe(Arrays.asList("test"));
        while (true){
            //消费数据
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                if (record.partition()==0){
                    String topic = record.topic();
                    int partition = record.partition();
                    long offset = record.offset();
                    String key = record.key();
                    String value = record.value();
                    System.out.println("topic：" + topic);
                    System.out.println("partition：" + partition);
                    System.out.println("offset：" + offset);
                    System.out.println("key：" + key);
                    System.out.println("value：" + value);
                    System.out.println("------------------------------");
                }

            }


        }
    }
}