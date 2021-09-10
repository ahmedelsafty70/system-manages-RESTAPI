package com.example.Phase12.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalException extends RuntimeException {

    public InternalException(String message) {
        super(message);
    }
}