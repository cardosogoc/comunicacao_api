package com.luizalebs.comunicacao_api.infraestructure.exceptions;

public class llegalArgumentException extends RuntimeException {
    public llegalArgumentException(String message) {
        super(message);
    }
    public llegalArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
