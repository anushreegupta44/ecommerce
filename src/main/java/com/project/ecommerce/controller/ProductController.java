package com.project.ecommerce.controller;

import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping("/{id}")
  public Product getProductDetails(@PathVariable("id") Integer productId) throws ProductNotFoundException {
    return productService.getProductById(productId);
  }

  @PostMapping
  public Product createProduct(@RequestBody @Valid Product product) {
    return productService.createProduct(product);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteProduct(@PathVariable("id") Integer productId) throws ProductNotFoundException {
    productService.remove(productId);
    return noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer productId, @RequestBody @Valid Product product) throws ProductNotFoundException {
    return new ResponseEntity(productService.updateProduct(productId, product), HttpStatus.OK);
  }

  @GetMapping
  public List<Product> getProductList() {
    return productService.getAllProducts();
  }
}
