package com.api.photo.storage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidMediaURLException extends ResponseStatusException {

    private static final HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    public InvalidMediaURLException(String url) {
        super(STATUS, String.format("The given URL: %s is invalid.", url));
    }
}
