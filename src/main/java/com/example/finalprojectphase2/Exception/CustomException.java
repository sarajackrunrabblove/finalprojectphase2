package com.example.finalprojectphase2.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);

    }
    public String getMessage() {
        return this.getLocalizedMessage();
    }

}