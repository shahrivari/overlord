package amu.saeed.overlord.io;

import com.google.common.base.Stopwatch;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kvs-1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer(props);
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1000000; i++)
            producer.send(new ProducerRecord<>("test", Integer.toBinaryString(i), Integer.toBinaryString(i)));
        producer.close();
        System.out.println(stopwatch);
    }
}
