package uz.aim.trellorestapi.exception;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:14
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public class GenericRuntimeException extends RuntimeException {
    public GenericRuntimeException(String message) {
        super(message);
    }
}
