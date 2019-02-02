package com.project.ecommerce.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String description;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "product_category",
      joinColumns = {@JoinColumn(name = "product_id")},
      inverseJoinColumns = {@JoinColumn(name = "category_id")})
  private List<Category> categories = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
}
