package it.polimi.deib.middleware.rest.solutions;

public class MissingOrderException extends Exception {
    public MissingOrderException(String orderID) {
        super(orderID);
    }
}
