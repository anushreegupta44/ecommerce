package com.project.ecommerce.exception;

import static com.project.ecommerce.constant.Constants.CUSTOMER_NOT_FOUND;

public class CartEmptyException extends RuntimeException {

  public CartEmptyException(String message) {
    super(message + CUSTOMER_NOT_FOUND);
  }
}
