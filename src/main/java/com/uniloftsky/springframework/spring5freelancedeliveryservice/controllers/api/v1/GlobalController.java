package com.uniloftsky.springframework.spring5freelancedeliveryservice.controllers.api.v1;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.BadRequestException;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@ControllerAdvice
public class GlobalController {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> handleAuth0NotFound(Exception exception) {
        return new ResponseEntity<>("Resource not found.\nMessage: " + exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(Exception exception) {
        return new ResponseEntity<>("Resource not found.\nMessage: " + exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(Exception exception) {
        return new ResponseEntity<>("Bad request.\nMessage: " + exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
