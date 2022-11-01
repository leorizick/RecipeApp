package com.leorizick.recipeapp.services.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends RestException {

    public EmailAlreadyInUseException() {
        super(HttpStatus.CONFLICT, "Credentials exception", "This e-mail is already in use");
    }
}
