package com.meal.controller.ExceptionHandlers;

import com.meal.service.Exception.ForbiddenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ForbiddenExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { ForbiddenException.class })
  protected ResponseEntity<Object> handleSecureConflict(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    if(bodyOfResponse == null) {
      bodyOfResponse = "Forbidden";
    }
    return handleExceptionInternal(ex, bodyOfResponse,
            new HttpHeaders(), HttpStatus.FORBIDDEN, request);
  }


}