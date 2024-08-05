package com.strong.AuthorService.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<?> handleException(AuthorException exception) {
        AuthorExcResponse response = new AuthorExcResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }
}
