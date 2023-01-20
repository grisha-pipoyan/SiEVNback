package com.brutus.sievn.exception;

public class NotAuthorized extends RuntimeException{
    public NotAuthorized(String message) {
        super(message);
    }

    public NotAuthorized(String message, Throwable cause) {
        super(message, cause);
    }
}
