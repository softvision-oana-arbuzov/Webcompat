package helpers;

/**
 * Exception to be thrown when a page is not valid
 *
 */
public class PageNotValidException extends PageException {

    private static final long serialVersionUID = 1L;

    public PageNotValidException(final String message) {
        super(message);
    }
}
