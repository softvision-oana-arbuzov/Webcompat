package helpers;

/**
 * Base class for all page related exceptions
 *
 */
public abstract class PageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PageException(final String message) {
        super(message);
    }
}

