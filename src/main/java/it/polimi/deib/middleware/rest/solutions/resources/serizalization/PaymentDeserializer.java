package it.polimi.deib.middleware.rest.solutions.resources.serizalization;

import it.polimi.deib.middleware.rest.solutions.resources.Payment;
import org.apache.kafka.common.serialization.Deserializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map;

public class PaymentDeserializer implements Deserializer<Payment> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Payment deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Payment order = null;
        try {
            order = mapper.readValue(data, Payment.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void close() {

    }
}
