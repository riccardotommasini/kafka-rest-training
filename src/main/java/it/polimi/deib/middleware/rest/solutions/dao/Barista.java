package it.polimi.deib.middleware.rest.solutions.dao;

import com.google.gson.Gson;
import it.polimi.deib.middleware.rest.commons.resources.Payment;
import it.polimi.deib.middleware.rest.commons.resources.serizalization.PaymentDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@WebSocket
public class Barista {

    private Logger logger = LoggerFactory.getLogger(Barista.class);

    private KafkaConsumer<String, Payment> baristac;

    private List<Session> users = new ArrayList<>();

    static final String read = "payments";

    private Map<String, Payment> payments = new HashMap<>();
    private Random random = new Random(2);

    private Gson gson = new Gson();

    public Barista() {

        Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, "baristac" + random.nextInt(100));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PaymentDeserializer.class);

        this.baristac = new KafkaConsumer<>(props);
        this.baristac.subscribe(Arrays.asList(read));

        Thread t = new Thread(() -> {
            baristac.poll(100);
            Set<TopicPartition> assignment = baristac.assignment();
            baristac.seekToBeginning(assignment);

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
        users.add(user);
        logger.info("A user joined the chat");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        users.remove(user);
        logger.info("A user left the chat");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
    }

    private void broadcast(String message) {
        users.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
