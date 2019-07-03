package cn.itcast;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;
import java.util.Properties;

public class KafkaProducerApi {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        // 服务器ip
        properties.setProperty("bootstrap.servers","node01:9092");
        // 属性键值对都序列化成字符串
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        /**
         * acks:发送数据是否需要应答
         * 1：leader做出应答
         * 0：leader节点不做出应答
         * -1：all:follower->leader->producer 所有节点同步成功之后才能应答
         */
        properties.setProperty("acks","1");
        // 创建一个生产者客服端实例
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        int i=1;
        while (i<1000){
            //封装一条数据
            ProducerRecord<String,String> record = new ProducerRecord<>("helloTopic",null,""+i);
            producer.send(record);
            Thread.sleep(1000);
            i++;
        }
        producer.close();
        System.out.println("end");

    }
}
