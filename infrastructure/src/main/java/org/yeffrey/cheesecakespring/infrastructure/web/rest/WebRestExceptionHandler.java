package org.yeffrey.cheesecakespring.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yeffrey.cheesecakespring.activities.core.ResourceNotFoundException;

@RestControllerAdvice
public class WebRestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

}
