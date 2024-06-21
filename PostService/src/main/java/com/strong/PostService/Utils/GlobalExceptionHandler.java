package com.strong.PostService.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<?> handleException(BlogException exception) {
        BlogExcResponse response = new BlogExcResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }
}
