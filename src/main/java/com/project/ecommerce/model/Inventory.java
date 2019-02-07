package com.project.ecommerce.model;

import javax.persistence.CascadeType;
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

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Boolean getSold() {
    return sold;
  }

  public void setSold(Boolean sold) {
    this.sold = sold;
  }
}
