package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@ControllerAdvice
public class GlobalController {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> handleNotFound(Exception exception) {
        return new ResponseEntity<Object>("Resource not found.\nMessage: " + exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
