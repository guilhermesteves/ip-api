package models.common;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public class DisplayableException extends RuntimeException {

    ObjectNode errors;

    public DisplayableException() {
        super();
    }

    public DisplayableException(String message,
                                Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DisplayableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisplayableException(String message) {
        super(message);
    }

    public DisplayableException(ObjectNode errors) {
        super();
        this.errors = errors;
    }

    public DisplayableException(Throwable cause) {
        super(cause);
    }

    public ObjectNode getErrors() {
        return errors;
    }

    private static final long serialVersionUID = -7480991203138773817L;
}
