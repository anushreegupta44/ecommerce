package com.project.ecommerce.util;

import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
  @ExceptionHandler(ProductNotFoundException.class)
  public void productNotFoundExceptionHandler() {
    System.out.print("Product not found Exception\n");
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        ex.getMessage());
    return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
  }
}

