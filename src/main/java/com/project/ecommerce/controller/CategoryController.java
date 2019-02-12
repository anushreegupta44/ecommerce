package com.project.ecommerce.controller;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  @PostMapping("/categories")
  public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
    Category savedCategory = categoryService.createCategory(category);
    return ResponseEntity.status(HttpStatus.OK).body(savedCategory);
  }


}
