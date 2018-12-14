package it.polimi.deib.middleware.rest.partials.api;

import it.polimi.deib.middleware.rest.solutions.api.AbstractService;
import org.slf4j.LoggerFactory;

import static spark.Spark.init;
import static spark.Spark.port;

public class BaristaServiceP extends AbstractService {

    public static void main(String[] args) {

        logger = LoggerFactory.getLogger(BaristaServiceP.class);
        port(4040);
        //TODO set up websocket handler in BaristaStub.class
        //TODO open a websocket
        init();
    }
}
