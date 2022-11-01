package com.leorizick.recipeapp.services.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyDeletedException extends RestException {

    private final static String TITLE = "%s is already deleted";

    private final static String DESCRIPTION = "The %s %s selected to be deleted was already disabled. No one operation was executed.";

    public AlreadyDeletedException(Class classType, String id) {
        super(HttpStatus.BAD_REQUEST,
                String.format(TITLE, classType.getSimpleName()),
                String.format(DESCRIPTION, classType.getSimpleName(), id));
    }

}
