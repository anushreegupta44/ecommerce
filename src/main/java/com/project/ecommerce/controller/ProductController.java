package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.CategoryService;
import com.project.ecommerce.service.ProductService;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  ProductService productService;

  @Autowired
  CategoryService categoryService;

  @GetMapping("/{id}")
  public Product getProductDetails(@PathVariable("id") Integer productId) throws ProductNotFoundException {
    return productService.getProductById(productId);
  }

  @PostMapping
  public Product createProduct(@RequestBody @Valid Product product) {
    return productService.createProduct(product);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteProduct(@PathVariable("id") Integer productId) {
    ValidationResponse res = productService.validateProductWithIdExists(productId);
    if (res.getErrors() != null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res.getErrors());
    } else {
      productService.remove(productId);
      return noContent().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity updateProduct(@PathVariable("id") Integer productId, @RequestBody @Valid ProductDto productDto) {
    ValidationResponse res = productService.validateProductWithIdExists(productId);
    if (res.getErrors() != null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res.getErrors());
    } else {
      productService.updateProduct(productId, productDto);
      return noContent().build();
    }
  }

}
