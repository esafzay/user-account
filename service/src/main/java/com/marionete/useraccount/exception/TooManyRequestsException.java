package com.marionete.useraccount.exception;

public class TooManyRequestsException extends Exception {

    public TooManyRequestsException(String message) {
        super(message);
    }
}
