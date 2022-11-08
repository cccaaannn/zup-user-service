package com.can.zupuserservice.core.exception;

public class JWTException extends RuntimeException{
    public JWTException() {
        super("JWT exception");
    }
    public JWTException(String message) {
        super(message);
    }
}