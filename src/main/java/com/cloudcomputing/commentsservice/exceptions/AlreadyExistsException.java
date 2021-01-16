package com.cloudcomputing.commentsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -9178161196467323195L;

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}