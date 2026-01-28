package com.redactado.raisu.exception;

import java.io.Serial;

public class EncodeException extends RaisuException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EncodeException(String message) {
        super(message);
    }

    public EncodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
