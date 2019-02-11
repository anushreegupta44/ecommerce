package com.project.ecommerce.controller;

import com.project.ecommerce.exception.*;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @PostMapping("/{cartId}/product/{productId}")
  public ResponseEntity addItemToCart(@PathVariable("cartId") Integer cartId,
                                      @PathVariable("productId") Integer productId)
      throws InventoryNotFoundException, CartNotFoundException, InventoryAlreadyInCartException {
    cartService.addProductToCart(cartId, productId);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping("/{cartId}/product/{productId}")
  public ResponseEntity deleteItemFromCart(@PathVariable("cartId") Integer cartId,
                                           @PathVariable("productId") Integer productId) throws InventoryNotFoundException {
    cartService.removeProductFromCart(cartId, productId);
    return noContent().build();
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<List> getItemsInCart(@PathVariable("cartId") Integer cartId) {
    return new ResponseEntity(cartService.getItemsInCart(cartId), HttpStatus.OK);
  }

  //Returns the id of the created order
  @PostMapping("/{cartId}/checkout")
  public ResponseEntity<Order> checkoutCart(@PathVariable("cartId") Integer cartId) throws CartEmptyException, CustomerNotFoundException {
    return new ResponseEntity(cartService.checkoutCart(cartId), HttpStatus.OK);
  }

}