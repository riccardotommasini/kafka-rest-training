package it.polimi.deib.middleware.rest.solutions.api;

import it.polimi.deib.middleware.rest.commons.Resp;
import it.polimi.deib.middleware.rest.partials.dao.WaiterP;
import it.polimi.deib.middleware.rest.commons.resources.Order;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class WaiterService extends AbstractService {
    static WaiterP waiter = new WaiterP();

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(WaiterService.class);
        port(4242);
        path("/waiter", () -> {
            before("/*", (q, a) -> logger.info("WaiterStub Received api call"));
            path("/orders", () -> {
                post("", (request, response) -> {
                    response.type("application/json");
                    response.status(SUCCESS);
                    String body = request.body();
                    if (body != null && !body.isEmpty()) {
                        String id = waiter.place(gson.fromJson(body, Order.class));
                        return gson.toJson(new Resp(SUCCESS, "Order Created with id [" + id + "]"));
                    } else
                        return gson.toJson(new Resp(400, "Bad Request"));
                });
            });
        });
    }
}
