package com.redactado.raisu.exception;

import java.io.Serial;

public class RaisuException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RaisuException(String message) {
        super(message);
    }

    public RaisuException(String message, Throwable cause) {
        super(message, cause);
    }
}
