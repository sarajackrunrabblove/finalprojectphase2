package com.example.finalprojectphase2.exception.error;

import com.example.finalprojectphase2.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleRequestParameterConstraintExceptions(CustomException e) {
        HttpStatus status = e.getStatus();
        return ResponseEntity.status(status).body(new ErrorResponse(status, e.getMessage()));
    }

}
