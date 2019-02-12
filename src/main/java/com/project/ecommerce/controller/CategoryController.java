package com.project.ecommerce.controller;

import com.project.ecommerce.exception.CategoryNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PutMapping("/categories/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer categoryId, @RequestBody @Valid Category category) throws CategoryNotFoundException {
    return new ResponseEntity(categoryService.updateCategory(categoryId, category), HttpStatus.OK);
  }


}
