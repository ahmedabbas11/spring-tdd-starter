package com.demo.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotAuthorizedException extends BaseException {

    public NotAuthorizedException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
