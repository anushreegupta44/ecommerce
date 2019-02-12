package com.project.ecommerce.controller;

import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

  @Autowired
  private InventoryService inventoryService;

  @PostMapping("/{inventorySku}/product/{productId}")
  public ResponseEntity<Inventory> addInventoryForProduct(@PathVariable("inventorySku") String inventorySku,
                                                          @PathVariable("productId") Integer productId) throws ProductNotFoundException {
    return new ResponseEntity(inventoryService.addInventoryForProduct(inventorySku, productId), HttpStatus.OK);
  }
}
