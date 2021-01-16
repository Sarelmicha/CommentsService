package com.cloudcomputing.commentsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -9178161196467323195L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}