package com.can.zupuserservice.core.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super("Not authorized");
    }
    public ForbiddenException(String message) {
        super(message);
    }
}