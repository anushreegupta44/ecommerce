package com.project.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {
  private String name;
  private String description;
  private List<CategoryDto> categories = new ArrayList<>();
  private Integer availableQuantity;

  public ProductDto() {
  }

  public ProductDto(String name, String description, List<CategoryDto> categories) {
    this.name = name;
    this.description = description;
    this.categories = categories;
  }

  public ProductDto(String name, String description, List<CategoryDto> categories, Integer availableQuantity) {
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

  public List<CategoryDto> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryDto> categories) {
    this.categories = categories;
  }

  public Integer getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Integer availableQuantity) {
    this.availableQuantity = availableQuantity;
  }
}
