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
  public ResponseEntity<Product> getProductDetails(@PathVariable("id") Integer productId) throws ProductNotFoundException {
    return new ResponseEntity(productService.getProductById(productId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
    ResponseEntity requestDtoError = validateProductDto(productDto);
    if (requestDtoError != null) {
      return requestDtoError;
    }
    Integer productId = productService.createProduct(productDto);
    if (productId != null) {
      return created(URI.create("/product/" + productId)).build();
    } else
      return notFound().build();
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

  private ResponseEntity validateProductDto(ProductDto productDto) {
    Map<String, String> errorMessages = new HashMap<>();
    if (isNull(productDto.getName())) {
      errorMessages.put("product", "product.not.present");
    }
    if (isNull(productDto.getCategories())) {
      errorMessages.put("product_category", "product_category.not.present");
    }
    List<Category> categoryList = categoryService.validateCategories(productDto.getCategories());
    if (isNull(categoryList)) {
      errorMessages.put("product_categories", "product_category.does.not.exist");
    }
    if (!errorMessages.isEmpty()) {
      return unprocessableEntity().body(errorMessages);
    } else
      return null;
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
  @ExceptionHandler(ProductNotFoundException.class)
  public void productNotFoundExceptionHandler() {
    System.out.print("Product not found Exception");
  }

}
