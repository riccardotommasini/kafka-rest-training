package it.polimi.deib.middleware.rest.partials.dao;

import it.polimi.deib.middleware.rest.commons.resources.Order;
import it.polimi.deib.middleware.rest.commons.resources.Payment;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CashierP {

    private KafkaProducer<String, Payment> cashierp;
    private KafkaConsumer<String, Order> cashierc;

    static final String read = "orders";
    static final String write = "payments";

    private Map<String, Order> orders = new HashMap<>();

    public CashierP() {

        Properties props = new Properties();

        //TODO SET UP PRODUCER PROPERTIES serveR, serialization...

        //TODO create PRODUCER

        props = new Properties();

        //TODO SET UP CONSUMER PROPERTIES (gid, server, offset, deserialization...

        //TODO create consumer and subscribe to topic


        Thread t = new Thread(() -> {
            //TODO poll the beginning of the topic and set up a periodic poll
        });

        t.start();
    }

    public void poll() {
        //TODO SAVE THE ORDERS
    }

    public String place(String orderID, Payment payment) {
        poll();

        Order order = this.orders.get(orderID);
        if (order != null) {
            //TODO CREATE THE RECORD
        }
        //TODO MISSING ORDER

        return "";
    }


    public void close() {
        cashierc.close();
        cashierp.close();
    }
}
