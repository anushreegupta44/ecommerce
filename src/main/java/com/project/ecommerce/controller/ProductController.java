package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ProductController {

  @Autowired
  ProductService productService;

  //Get the details of a product, its quantity in stock and the categories it belongs to
  @GetMapping("/product/{pid}")
  public ResponseEntity getProductDetails(@PathVariable("pid") Integer productId) {
    ProductDto productDto = productService.getProductById(productId);
    return productDto == null ? noContent().build() : ok(productDto);
  }
}
