package com.leorizick.recipeapp.services.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class RestException extends RuntimeException {

    private final HttpStatus statusCode;

    private final String title;

    private final String description;

    public RestException(HttpStatus statusCode, String title, String description) {
        super(title);
        this.statusCode = statusCode;
        this.title = title;
        this.description = description;
    }

    public RestException(HttpStatus statusCode, String title) {
        this(statusCode, title, null);
    }
}
