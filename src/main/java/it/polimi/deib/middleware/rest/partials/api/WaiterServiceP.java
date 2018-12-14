package it.polimi.deib.middleware.rest.partials.api;

import it.polimi.deib.middleware.rest.solutions.api.AbstractService;
import it.polimi.deib.middleware.rest.partials.dao.WaiterP;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class WaiterServiceP extends AbstractService {
    static WaiterP waiter = new WaiterP();

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(WaiterServiceP.class);
        port(4242);
        path("/waiter", () -> {
            before("/*", (q, a) -> logger.info("WaiterStub Received api call"));
            path("/orders", () -> {
                //TODO post a new order
            });
        });
    }
}
