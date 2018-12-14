package it.polimi.deib.middleware.rest.partials.dao;

import it.polimi.deib.middleware.rest.commons.resources.Order;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;
import java.util.UUID;

public class WaiterP {

    KafkaProducer<String, Order> waiter;
    static final String topic = "orders";

    public WaiterP() {

        Properties props = new Properties();
        //TODO SET UP PRODUCER PROPERTIES serveR, serialization...

        //TODO create PRODUCER

    }


    public String place(Order oder) {
        String s = UUID.randomUUID().toString().split("-")[0];
        oder.setId(s);
        //TODO CREATE RECORD
        return s;
    }


    public void close() {
        waiter.close();
    }
}
