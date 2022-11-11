package com.can.zupuserservice.config;

import com.can.zupuserservice.core.exception.ForbiddenException;
import com.can.zupuserservice.core.security.jwt.exceptions.JWTException;
import com.can.zupuserservice.core.exception.NotFoundException;
import com.can.zupuserservice.core.utilities.result.concretes.ErrorResult;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerConfig {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult("Server error"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> notAuthorizedException(ForbiddenException ex) {
//        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException ex) {
//        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({TypeMismatchException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> notFoundException(TypeMismatchException ex) {
//        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult("There is a type mismatch"), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> notFoundException(HttpMessageConversionException ex) {
//        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult("Provided http method is not supported"), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class, ConstraintViolationException.class})
    public ResponseEntity<Object> notFoundException(ValidationException ex) {
//        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult("Parameter validation failed"), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {JWTException.class})
    public ResponseEntity<Object> jwtVerificationException(JWTException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResult("Not authorized"), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new ErrorResult("Arguments not valid", errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}