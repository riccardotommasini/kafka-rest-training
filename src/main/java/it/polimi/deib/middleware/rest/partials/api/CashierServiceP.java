package it.polimi.deib.middleware.rest.partials.api;

import it.polimi.deib.middleware.rest.solutions.api.AbstractService;
import it.polimi.deib.middleware.rest.partials.dao.CashierP;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class CashierServiceP extends AbstractService {
    static CashierP cashier = new CashierP();


    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(CashierServiceP.class);
        port(4141);
        path("/cashier", () -> {
            before("/*", (q, a) -> logger.info("Received api call"));
            path("/payments", () -> {
                //TODO post payment using order ID, if exists
            });
        });
    }
}
