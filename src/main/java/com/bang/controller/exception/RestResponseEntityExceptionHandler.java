/**
 * 
 */
package com.bang.controller.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bang.model.Job;
import com.bang.model.ServiceProvider;

@ControllerAdvice(basePackages = {"com.bang.controller"})
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Either you sent illegal arguments or the state of the instance was not legal";
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    
    @ExceptionHandler(value = {JobNotFoundException.class})
    protected ResponseEntity<Object> handleJobNotFound(RuntimeException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	//ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
    	return new ResponseEntity<Object>(ex.getMessage(), headers, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = {ServiceProviderNotFoundException.class})
    protected ResponseEntity<Object> handleServiceProviderNotFound(RuntimeException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	//return new ResponseEntity<Object>(ex.getMessage(), headers, HttpStatus.NOT_FOUND);
    	String bodyOfResponse = "You are not registered as a service provider";
    	return handleExceptionInternal(ex, bodyOfResponse, 
    	          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    	
    }
    
    @ExceptionHandler(value = {CustomerExistsException.class})
    protected ResponseEntity<Object> handleCustomerExists(RuntimeException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	return new ResponseEntity<Object>(ex.getMessage(), headers, HttpStatus.CONFLICT);
    }
}
