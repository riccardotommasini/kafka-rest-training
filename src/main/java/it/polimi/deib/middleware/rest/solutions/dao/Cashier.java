package it.polimi.deib.middleware.rest.solutions.dao;

import it.polimi.deib.middleware.rest.solutions.MissingOrderException;
import it.polimi.deib.middleware.rest.solutions.resources.Order;
import it.polimi.deib.middleware.rest.solutions.resources.Payment;
import it.polimi.deib.middleware.rest.solutions.resources.serizalization.OrderDeserializer;
import it.polimi.deib.middleware.rest.solutions.resources.serizalization.PaymentSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;

public class Cashier {

    private KafkaProducer<String, Payment> cashierp;
    private KafkaConsumer<String, Order> cashierc;

    static final String read = "orders";
    static final String write = "payments";

    private Map<String, Order> orders = new HashMap<>();

    public Cashier() {

        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PaymentSerializer.class.getName());

        this.cashierp = new KafkaProducer<>(props);

        props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "cashierc" + UUID.randomUUID().toString());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class);

        this.cashierc = new KafkaConsumer<>(props);
        this.cashierc.subscribe(Arrays.asList(read));


        Thread t = new Thread(() -> {
            cashierc.poll(100);
            Set<TopicPartition> assignment = cashierc.assignment();
            cashierc.seekToBeginning(assignment);


            while (true) {
                System.out.println("Polling");
                poll();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    public void poll() {
        ConsumerRecords<String, Order> orders = cashierc.poll(Duration.ofMillis(300));
        orders.forEach(stringOrderConsumerRecord -> this.orders.put(stringOrderConsumerRecord.key(), stringOrderConsumerRecord.value()));
    }

    public String place(String orderID, Payment payment) throws MissingOrderException {
        poll();

        Order order = this.orders.get(orderID);
        if (order != null) {
            payment.setOrder(order);
            cashierp.send(new ProducerRecord<>(write, orderID, payment));
            return orderID;
        }
        throw new MissingOrderException(orderID);
    }


    public void close() {
        cashierc.close();
        cashierp.close();
    }
}
