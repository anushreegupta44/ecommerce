package com.project.ecommerce.controller;

import com.project.ecommerce.exception.CartNotFoundException;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.service.CartService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @PostMapping("/{cartId}/product/{productId}")
  public ResponseEntity addItemToCart(@PathVariable("cartId") Integer cartId,
                                      @PathVariable("productId") Integer productId) throws InventoryNotFoundException, CartNotFoundException, DataIntegrityViolationException, ConstraintViolationException {
    cartService.addProductToCart(cartId, productId);
    return null;
  }
}
