package uz.aim.trellorestapi.exception;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:14
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public class GenericNotFoundException extends GenericRuntimeException {
    public GenericNotFoundException(String message) {
        super(message);
    }
}
