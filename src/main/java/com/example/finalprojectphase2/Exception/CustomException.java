package com.example.finalprojectphase2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;
    public CustomException() {
        super();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    @Override
    public String getMessage() {
        return super.getLocalizedMessage();
    }

}