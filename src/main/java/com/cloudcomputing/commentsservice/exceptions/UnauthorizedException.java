package com.cloudcomputing.commentsservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = -9178161196467323195L;

    public UnauthorizedException() {
        super();
    }
    
    public UnauthorizedException(String message) {
        super(message);
    }


}
