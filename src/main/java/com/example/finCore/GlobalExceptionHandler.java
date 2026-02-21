package com.example.finCore;

import com.example.finCore.entity.ErrorResponseBody;
import com.example.finCore.exception.ImmutableFieldException;
import com.example.finCore.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = NotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleResourceNotFound(NotFoundException ex){
        ErrorResponseBody error=new ErrorResponseBody(ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(exception = ImmutableFieldException.class)
    public  ResponseEntity<ErrorResponseBody> handleImmutableFieldException(ImmutableFieldException ex){
        ErrorResponseBody error=new ErrorResponseBody(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }





}
