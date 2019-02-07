package com.project.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Product {
  @Id
  @GeneratedValue
  private Integer id;
  @NotNull
  private String name;
  @NotNull
  private String description;

  @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @JoinTable(name = "product_category",
      joinColumns = {@JoinColumn(name = "product_id")},
      inverseJoinColumns = {@JoinColumn(name = "category_id")})
  private List<Category> categories;

  @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private List<Inventory> inventories;

  public Product() {
  }

  public Product(@NotNull String name, @NotNull String description, List<Category> categories, List<Inventory> inventories) {
    this.name = name;
    this.description = description;
    this.categories = categories;
    this.inventories = inventories;
  }

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

  public List<Inventory> getInventories() {
    return inventories;
  }

  public void setInventories(List<Inventory> inventories) {
    this.inventories = inventories;
  }
}
