package com.leorizick.recipeapp.services.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Component
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestErrorDto> handleRestException(RestException restException){

        var error = new RestErrorDto();
        error.setTitle(restException.getTitle());
        error.setDescription(restException.getDescription());

        return ResponseEntity.status(restException.getStatusCode())
                .body(error);
    }
}
