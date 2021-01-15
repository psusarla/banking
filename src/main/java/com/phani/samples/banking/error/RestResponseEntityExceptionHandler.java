package com.phani.samples.banking.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.UUID;

//TODO - We will be handling client side exceptions in this fashion. In the interest of time, it is only handling internal errors now.
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { RuntimeException.class })
  protected ResponseEntity<Object> handleServerSideErrors(RuntimeException ex, WebRequest request) {
    //Assuming these are external facing apis, care should be taken not to expose too much internal information in the error response. Otherwise, this could lead to security vulnerabilities
    String message = "Sorry, something went wrong";
    String incidentId = UUID.randomUUID().toString(); //This is so that client can report the incident id and support can dig up the logs
    Error error = new Error(message, incidentId, new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    StringBuilder sb = new StringBuilder("Exception occurred ").append(incidentId);
    log.error(sb.toString(), ex);
    return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
  }
}