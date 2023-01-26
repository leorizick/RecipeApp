package com.leorizick.recipeapp.services.exceptions;

import org.springframework.http.HttpStatus;

public class EmailException extends RestException {

    private final static String TITLE = "Fail to send email";

    private final static String DESCRIPTION = "Something dont happens with excpected";

    public EmailException() {
        super(HttpStatus.BAD_GATEWAY,
                String.format(TITLE),
                String.format(DESCRIPTION));
    }

}
