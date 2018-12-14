package it.polimi.deib.middleware.rest.commons;

public class MissingOrderException extends Exception {
    public MissingOrderException(String orderID) {
        super(orderID);
    }
}
