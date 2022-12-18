package com.marionete.useraccount.controller;

import com.marionete.useraccount.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle AuthenticationException by returning http unauthorized status with the message
     * @param ex AuthenticationException
     * @return ResponseEntity
     */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleException(AuthenticationException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    /**
     * Handle any exeption by logging its details and return a generic response to the client
     * @param ex Exception
     * @param request WebRequest
     * @return ResponseEntity
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex, WebRequest request) {

        log.error(request.getDescription(true), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

}
