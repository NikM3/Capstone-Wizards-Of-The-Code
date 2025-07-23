package wotc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) throws Exception {
        return ErrorResponse.build("Something unexpected happened! Unhandled error.");
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorResponse> forbiddenException(Exception ex) throws Exception {
        System.out.println("Forbidden error occured due to user auth");
        return ErrorResponse.build("I'm sorry Dave, I'm afraid I can't do that. You don't have permission.");
    }
}
