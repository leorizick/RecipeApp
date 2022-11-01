package com.leorizick.recipeapp.services.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException {

    public NotFoundException(String title, String description) {
        super(HttpStatus.NOT_FOUND, title, description);
    }

    public NotFoundException(String title) {
        super(HttpStatus.NOT_FOUND, title);
    }
}
