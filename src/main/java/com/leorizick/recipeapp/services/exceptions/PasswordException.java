package com.leorizick.recipeapp.services.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordException extends RestException {

    public PasswordException(String title, String description) {
        super(HttpStatus.UNAUTHORIZED, title, description);
    }

    public PasswordException(String title) {
        super(HttpStatus.UNAUTHORIZED, title);
    }
}
