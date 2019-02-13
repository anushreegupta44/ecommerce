package com.project.ecommerce.service;

import com.project.ecommerce.exception.CategoryNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  public Category getCategoryByName(String name) throws CategoryNotFoundException {
    return categoryRepository.getCategoryByName(name).orElseThrow(() -> new CategoryNotFoundException());
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Category updateCategory(Integer categoryId, Category category) throws CategoryNotFoundException {
    Category categoryToUpdate = this.getCategoryById(categoryId);
    categoryToUpdate.setName(category.getName());
    categoryToUpdate.setDescription(category.getDescription());
    return categoryRepository.save(categoryToUpdate);
  }

  public Category getCategoryById(Integer categoryId) throws CategoryNotFoundException {
    return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException());
  }

  @Transactional
  public void remove(Integer categoryId) throws CategoryNotFoundException {
    Category categoryToRemove = getCategoryById(categoryId);
    List<Product> products = productRepository.findAllByCategories(categoryToRemove);
    if (!isNull(products) || products.size() != 0) {
      products.forEach(product -> {
        product.getCategories().remove(categoryToRemove);
        productRepository.save(product);
      });
    }
    categoryToRemove.getProducts().removeAll(categoryToRemove.getProducts());
    categoryRepository.delete(categoryToRemove);
  }
}
