package ru.osslabs.graph.exception;

/**
 * Created by ikuchmin on 04.03.16.
 */
public class GraphException extends RuntimeException {
    public GraphException() {
    }

    public GraphException(String message) {
        super(message);
    }

    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphException(Throwable cause) {
        super(cause);
    }

    public GraphException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
