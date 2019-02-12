package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.exception.CategoryNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public Optional<Category> validateCategoryExists(String name) {
    return categoryRepository.getCategoryByName(name);
  }

  public List<Category> validateCategories(List<CategoryDto> categoryDtos) {
    List<Category> categories = new ArrayList<>();
    for (CategoryDto categoryDto :
        categoryDtos) {
      Optional<Category> category = categoryRepository.getCategoryByName(categoryDto.getName());
      if (category.isPresent()) {
        categories.add(category.get());
      }
    }
    if (categories.isEmpty()) {
      return null;
    } else {
      return categories;
    }

  }

  public Category getCategoryByName(String name) throws CategoryNotFoundException {
    return categoryRepository.getCategoryByName(name).orElseThrow(() -> new CategoryNotFoundException());
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }
}
