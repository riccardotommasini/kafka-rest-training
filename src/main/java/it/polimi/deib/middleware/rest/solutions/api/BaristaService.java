package it.polimi.deib.middleware.rest.solutions.api;

import it.polimi.deib.middleware.rest.partials.dao.BaristaP;
import org.slf4j.LoggerFactory;

import static spark.Spark.init;
import static spark.Spark.port;
import static spark.Spark.webSocket;

public class BaristaService extends AbstractService {

    public static void main(String[] args) {

        logger = LoggerFactory.getLogger(BaristaService.class);
        port(4040);
        webSocket("/barista", BaristaP.class);
        init();
    }
}
