package it.polimi.deib.middleware.rest.solutions.dao;

import it.polimi.deib.middleware.rest.solutions.resources.Order;
import it.polimi.deib.middleware.rest.solutions.resources.serizalization.OderSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class Waiter {

    Properties props = new Properties();
    KafkaProducer<String, Order> waiter;
    static final String topic = "orders";

    public Waiter() {

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OderSerializer.class.getName());
        this.waiter = new KafkaProducer<>(props);

    }


    public String place(Order oder) {

        String s = UUID.randomUUID().toString().split("-")[0];
        oder.setId(s);
        ProducerRecord<String, Order> record = new ProducerRecord<>(topic, s, oder);
        waiter.send(record);
        return s;
    }


    public void close() {
        waiter.close();
    }
}
