package com.workintech.zoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ZooGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handleException(ZooException zooException){
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(zooException.getStatus().value(),zooException.getMessage(),System.currentTimeMillis());
        return new ResponseEntity(zooErrorResponse, zooException.getStatus());
    }
    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handleException(Exception exception){
        ZooErrorResponse zooErrorResponse = new ZooErrorResponse(HttpStatus.BAD_REQUEST.value(),exception.getMessage(),  System.currentTimeMillis());
        return new ResponseEntity<>(zooErrorResponse,HttpStatus.BAD_REQUEST);
    }

}
