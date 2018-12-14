package it.polimi.deib.middleware.rest.solutions.api;

import com.google.gson.Gson;
import org.slf4j.Logger;

public abstract class AbstractService {
    protected static Logger logger;
    protected static final int SUCCESS = 201;
    protected static final Gson gson = new Gson();

}
