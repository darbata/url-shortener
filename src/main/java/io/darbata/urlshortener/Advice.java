package io.darbata.urlshortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advice {
    @ExceptionHandler(NoMatchingUrlException.class)
    public ResponseEntity<String> handleNoMatchingUrlException (NoMatchingUrlException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}