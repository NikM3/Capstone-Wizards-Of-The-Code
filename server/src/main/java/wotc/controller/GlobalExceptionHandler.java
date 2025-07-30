package wotc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generalExceptionHandler(Exception ex) throws Exception {
        return ErrorResponse.build("Something unexpected happened! Unhandled error: " + ex.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorResponse> forbiddenException(Exception ex) throws Exception {
        System.out.println("Forbidden error occurred due to user auth");
        return ErrorResponse.build("I'm sorry Dave, I'm afraid I can't do that. You don't have permission.");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> ioException(Exception ex) throws Exception {
        return ErrorResponse.build("Something went wrong while accessing a file: " + ex.getMessage());
    }
}
