package com.leorizick.recipeapp.services.exceptions;

import org.springframework.http.HttpStatus;

public class AccountTypeNotAllowed extends RestException{

        public AccountTypeNotAllowed(String type) {
            super(HttpStatus.BAD_REQUEST, "Access denied", String.format("Only a %s is allowed to do this", type));
        }

    }
