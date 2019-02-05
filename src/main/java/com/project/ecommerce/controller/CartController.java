package com.project.ecommerce.controller;

import com.project.ecommerce.service.CartItemService;
import com.project.ecommerce.service.CustomerService;
import com.project.ecommerce.service.InventoryService;
import com.project.ecommerce.service.ProductService;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ProductService productService;

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private CartItemService cartItemService;

  @PostMapping("/customer/{customerId}/product/{productId}")
  public ResponseEntity addItemToUserCart(@PathVariable("customerId") Integer customerId,
                                          @PathVariable("productId") Integer productId) {
    ValidationResponse customerValidation = customerService.validateCustomerExists(customerId);
    if (!customerValidation.getValid()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerValidation.getErrors());
    }

    ValidationResponse productValidation = productService.validateProductWithIdExists(productId);
    if (!productValidation.getValid()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productValidation.getErrors());
    }

    ValidationResponse inventoryValidationRes = inventoryService.validateInventoryForProductExists(productId);
    if (!inventoryValidationRes.getValid()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(inventoryValidationRes.getErrors());
    }
    cartItemService.addProductToCartForUser(customerId, productId);

    return created(URI.create("/cart/customer/" + customerId + "/product/" + productId)).build();
  }
}
