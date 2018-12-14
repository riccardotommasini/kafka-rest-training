package it.polimi.deib.middleware.rest.solutions.api;

import com.google.gson.Gson;
import org.slf4j.Logger;

public abstract class AbstractService {
    static Logger logger;
    static final int SUCCESS = 201;
    static final Gson gson = new Gson();

}
