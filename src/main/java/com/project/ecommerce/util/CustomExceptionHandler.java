package com.project.ecommerce.util;

import com.project.ecommerce.dto.ErrorDetails;
import com.project.ecommerce.exception.*;
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
  @ExceptionHandler({ProductNotFoundException.class})
  public void productNotFoundExceptionHandler() {
    System.out.print("Product not found Exception\n");
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found")
  @ExceptionHandler({CustomerNotFoundException.class})
  public void customerNotFoundExceptionHandler() {
    System.out.print("Customer not found Exception\n");
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category not found")
  @ExceptionHandler({CategoryNotFoundException.class})
  public void categoryNotFoundExceptionHandler() {
    System.out.print("Category not found Exception\n");
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Inventory empty for product")
  @ExceptionHandler({InventoryNotFoundException.class})
  public void inventoryNotFoundExceptionHandler() {
    System.out.print("Inventory not found Exception\n");
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cart empty for product")
  @ExceptionHandler({CartNotFoundException.class})
  public void cartNotFoundExceptionHandler() {
    System.out.print("Cart not found Exception\n");
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Inventory already mapped to cart.")
  @ExceptionHandler({InventoryAlreadyInCartException.class})
  public void cartAlreadyMappedToInventory() {
    System.out.print("Inventory already mapped to cart\n");
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cart has no products")
  @ExceptionHandler({CartEmptyException.class})
  public void cartEmptyException() {
    System.out.print("Cart has no products\n");
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        ex.getMessage());
    return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
  }
}

