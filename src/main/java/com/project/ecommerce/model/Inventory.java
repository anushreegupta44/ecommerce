package com.project.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Inventory {
  @Id
  private String sku;

  @ManyToOne
  private Product product;

  private Boolean sold;


}
