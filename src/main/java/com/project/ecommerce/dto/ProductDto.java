package com.project.ecommerce.dto;

import com.project.ecommerce.model.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {
  private String name;
  private String description;
  private List<Category> categories = new ArrayList<>();
  private Integer availableQuantity;

  public ProductDto() {
  }

  public ProductDto(String name, String description, List<Category> categories, Integer availableQuantity) {
    this.name = name;
    this.description = description;
    this.categories = categories;
    this.availableQuantity = availableQuantity;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public Integer getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Integer availableQuantity) {
    this.availableQuantity = availableQuantity;
  }
}
