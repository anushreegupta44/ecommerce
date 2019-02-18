package com.project.ecommerce.service;

import com.project.ecommerce.exception.CategoryNotFoundException;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryService categoryService;

  public Product getProductById(Integer productId) throws ProductNotFoundException {
    return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException());
  }

  public Product createProduct(Product productToAdd) {
    List<Category> categories = getValidCategories(productToAdd);
    productToAdd.setCategories(categories);
    return productRepository.save(productToAdd);
  }

  public void remove(Integer productId) throws ProductNotFoundException {
    this.getProductById(productId);
    productRepository.deleteById(productId);
  }

  public Product updateProduct(Integer productId, Product product) throws ProductNotFoundException {
    Product productToUpdate = this.getProductById(productId);
    productToUpdate.setName(product.getName());
    productToUpdate.setDescription(product.getDescription());
    List<Category> categoriesToUpdate = getValidCategories(product);
    productToUpdate.setCategories(Stream.concat(productToUpdate.getCategories().stream(), categoriesToUpdate.stream()).distinct().collect(Collectors.toList()));
    return productRepository.save(productToUpdate);
  }

  public List<Category> getValidCategories(Product product) {
    List<Category> categories = product.getCategories().stream().map(category -> {
      try {
        return categoryService.getCategoryByName(category.getName());
      } catch (CategoryNotFoundException e) {
        return null;
      }
    }).collect(Collectors.toList());
    categories.removeAll(Collections.singleton(null));
    return categories;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }
}

