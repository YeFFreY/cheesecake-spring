package org.yeffrey.cheesecakespring.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yeffrey.cheesecakespring.library.core.ResourceNotFoundException;

@RestControllerAdvice
public class WebRestExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }

/*    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, String> errorItems = new HashMap<>(5);
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorItems.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errorItems.put(error.getObjectName(), error.getDefaultMessage());
        }

        // FIXME create a dedicated object
        Map<String, Object> errors = new HashMap<>(1);
        errors.put("errors", errorItems);
        return new ResponseEntity<>(errors, status);
    }*/
}
