package kloSpringServer;

/**
 * Created with IntelliJ IDEA.
 * User: Joona
 * Date: 17.12.2013
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
