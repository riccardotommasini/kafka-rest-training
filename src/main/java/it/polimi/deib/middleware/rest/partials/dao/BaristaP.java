package it.polimi.deib.middleware.rest.partials.dao;

import com.google.gson.Gson;
import it.polimi.deib.middleware.rest.commons.resources.Payment;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;

@WebSocket
public class BaristaP {

    private Logger logger = LoggerFactory.getLogger(BaristaP.class);

    private KafkaConsumer<String, Payment> baristac;

    static final String topic = "payments";

    private Gson gson = new Gson();

    public BaristaP() {

        Properties props = new Properties();

        //TODO SET UP CONSUMER PROPERTIES (gid, server, offset, deserialization...

        //TODO create consumer and subscribe to topic

        Thread t = new Thread(() -> {
            //TODO poll the beginning of the topic and set up a periodic poll
        });

        t.start();

    }

    public synchronized void poll() {
        //TODO set a join, requires polling from two topics
        ConsumerRecords<String, Payment> orders = baristac.poll(Duration.ofMillis(500));
        orders.forEach(pr -> broadcast(gson.toJson(pr.value())));
    }

    public void close() {
        baristac.close();
    }

    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        //TODO
        logger.info("A user joined the chat");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        //TODO
        logger.info("A user left the chat");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        //DO nothing

    }

    private void broadcast(String message) {
        //TODO
    }
}
