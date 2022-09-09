package com.can.zupuserservice.core.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super("Forbidden");
    }
    public ForbiddenException(String message) {
        super(message);
    }
}