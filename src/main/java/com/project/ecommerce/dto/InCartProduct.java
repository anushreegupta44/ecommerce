package com.project.ecommerce.dto;

public class InCartProduct {
  private Integer productId;
  private Long count;
  private String name;
  private String description;

  public InCartProduct() {
  }

  public InCartProduct(Integer productId, Long count, String name, String description) {
    this.productId = productId;
    this.count = count;
    this.name = name;
    this.description = description;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
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
}
