package com.cloudcomputing.commentsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = -9178161196467323195L;

    public BadRequestException() {
        super();
    }
    
    public BadRequestException(String message) {
        super(message);
    }


}
