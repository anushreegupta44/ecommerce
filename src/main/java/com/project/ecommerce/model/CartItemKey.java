package com.project.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartItemKey implements Serializable {
  @Column(name = "customer_id")
  private Integer customerId;

  @Column(name = "item_sku")
  private String itemSku;

  public CartItemKey() {
  }

  public CartItemKey(Integer customerId, String itemSku) {
    this.customerId = customerId;
    this.itemSku = itemSku;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  public String getItemSku() {
    return itemSku;
  }

  public void setItemSku(String itemSku) {
    this.itemSku = itemSku;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItemKey that = (CartItemKey) o;
    return Objects.equals(getCustomerId(), that.getCustomerId()) &&
        Objects.equals(getItemSku(), that.getItemSku());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCustomerId(), getItemSku());
  }
}
