package com.project.ecommerce.exception;

import static com.project.ecommerce.constant.Constants.CART_NOT_FOUND;

public class CartEmptyException extends Exception {

  public CartEmptyException(String message) {
    super(message + CART_NOT_FOUND);
  }
}
