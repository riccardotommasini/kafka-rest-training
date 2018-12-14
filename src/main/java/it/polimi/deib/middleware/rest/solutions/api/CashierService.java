package it.polimi.deib.middleware.rest.solutions.api;

import it.polimi.deib.middleware.rest.commons.Resp;
import it.polimi.deib.middleware.rest.commons.MissingOrderException;
import it.polimi.deib.middleware.rest.solutions.dao.Cashier;
import it.polimi.deib.middleware.rest.commons.resources.Payment;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class CashierService extends AbstractService {
    static Cashier cashier = new Cashier();


    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(CashierService.class);
        port(4141);
        path("/cashier", () -> {
            before("/*", (q, a) -> logger.info("Received api call"));
            path("/payments", () -> {
                post("/:order", (request, response) -> {
                    String id = request.params(":order");
                    try {
                        response.type("application/json");
                        response.status(SUCCESS);
                        cashier.place(id, gson.fromJson(request.body(), Payment.class));
                        return gson.toJson(new Resp(SUCCESS, "Order with id [" + id + "] successfully payed"));
                    } catch (MissingOrderException e) {
                        return gson.toJson(new Resp(400, "No order with id [" + id + "]"));
                    }
                });
            });
        });
    }
}
