package com.redactado.raisu.exception;

import java.io.Serial;

public class PasteException extends RaisuException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PasteException(String message) {
        super(message);
    }

    public PasteException(String message, Throwable cause) {
        super(message, cause);
    }
}
