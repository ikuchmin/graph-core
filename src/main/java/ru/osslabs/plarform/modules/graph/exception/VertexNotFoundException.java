package ru.osslabs.plarform.modules.graph.exception;

/**
 * Created by ikuchmin on 04.03.16.
 */
public class VertexNotFoundException extends GraphException {
    public VertexNotFoundException() {
    }

    public VertexNotFoundException(String message) {
        super(message);
    }

    public VertexNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VertexNotFoundException(Throwable cause) {
        super(cause);
    }

    public VertexNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
