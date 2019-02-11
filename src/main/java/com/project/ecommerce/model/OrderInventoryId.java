package com.project.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderInventoryId implements Serializable {
  @Column(name = "ORDER_ID")
  private Integer orderId;

  @Column(name = "INVENTORY_SKU")
  private String inventorySku;

  public OrderInventoryId() {
  }

  public OrderInventoryId(Integer orderId, String inventorySku) {
    this.orderId = orderId;
    this.inventorySku = inventorySku;
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public String getInventorySku() {
    return inventorySku;
  }

  public void setInventorySku(String inventorySku) {
    this.inventorySku = inventorySku;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderInventoryId that = (OrderInventoryId) o;
    return Objects.equals(getOrderId(), that.getOrderId()) &&
        Objects.equals(getInventorySku(), that.getInventorySku());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOrderId(), getInventorySku());
  }
}
