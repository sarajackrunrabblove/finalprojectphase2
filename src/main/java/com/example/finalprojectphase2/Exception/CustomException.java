package com.example.finalprojectphase2.Exception;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String message;

    public CustomException(String message) {
        this.message = message;

    }
    public String getMessage() {
        return this.message;
    }

}