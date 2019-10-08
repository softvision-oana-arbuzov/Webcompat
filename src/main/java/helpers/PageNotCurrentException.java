package helpers;

/**
 * Exception to be thrown when a page is not current as expected
 *
 */
public class PageNotCurrentException extends PageException {

    private static final long serialVersionUID = 1L;

    public PageNotCurrentException(final String message) {
        super(message);
    }
}
