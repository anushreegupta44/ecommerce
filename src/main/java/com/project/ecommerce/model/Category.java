package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  @JsonIgnore
  @ManyToMany(mappedBy = "categories")
  private List<Product> products = new ArrayList<>();
}
