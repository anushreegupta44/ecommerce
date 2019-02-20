package com.project.ecommerce.dto;

public class OrderDetail {
  private Integer productId;
  private String name;
  private String description;
  private Long pricePerUnit;
  private Long count;

  public OrderDetail() {
  }

  public OrderDetail(Integer productId, String name, String description, Long pricePerUnit, Long count) {
    this.productId = productId;
    this.name = name;
    this.description = description;
    this.pricePerUnit = pricePerUnit;
    this.count = count;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
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

  public Long getPricePerUnit() {
    return pricePerUnit;
  }

  public void setPricePerUnit(Long pricePerUnit) {
    this.pricePerUnit = pricePerUnit;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }
}
