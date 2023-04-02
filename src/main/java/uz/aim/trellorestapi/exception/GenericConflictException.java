package uz.aim.trellorestapi.exception;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:15
 * Project : Trello-REST-API / IntelliJ IDEA
 */

public class GenericConflictException extends GenericRuntimeException{
    public GenericConflictException(String message) {
        super(message);
    }
}
