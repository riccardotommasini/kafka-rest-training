package it.polimi.deib.middleware.rest.solutions.resources.serizalization;

import it.polimi.deib.middleware.rest.solutions.resources.Order;
import org.apache.kafka.common.serialization.Deserializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map;

public class OrderDeserializer implements Deserializer<Order> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Order deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Order order = null;
        try {
            order = mapper.readValue(data, Order.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void close() {

    }
}
