package ee.mihkel.webshop.controller.exception;

public class EmailExistsException extends Throwable {

    public EmailExistsException(String message) {
        super(message);
    }

}
