package com.kurtcan.zupuserservice.core.security.jwt.exceptions;

public class JWTException extends RuntimeException{
    public JWTException() {
        super("JWT exception");
    }
    public JWTException(String message) {
        super(message);
    }
}