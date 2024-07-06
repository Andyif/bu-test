package org.bu.test.butest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorRes> handleValidationException(HandlerMethodValidationException ex) {
        final var error = new ErrorRes(Objects.requireNonNull(ex.getDetailMessageArguments())[0]);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    public record ErrorRes(Object errorMsg){
    }
}
