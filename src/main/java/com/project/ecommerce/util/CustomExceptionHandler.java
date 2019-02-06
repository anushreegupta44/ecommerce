package com.project.ecommerce.util;

import com.project.ecommerce.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
  @ExceptionHandler(ProductNotFoundException.class)
  public void productNotFoundExceptionHandler() {
    System.out.print("Product not found Exception\n");
  }

}
