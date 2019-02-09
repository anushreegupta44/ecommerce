package com.project.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartInventoryId implements Serializable {
  @Column(name = "CART_ID")
  private Integer cartId;

  @Column(name = "INVENTORY_SKU")
  private String inventorySku;

  public CartInventoryId() {
  }

  public CartInventoryId(Integer cartId, String inventorySku) {
    this.cartId = cartId;
    this.inventorySku = inventorySku;
  }

  public Integer getCartId() {
    return cartId;
  }

  public void setCartId(Integer cartId) {
    this.cartId = cartId;
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
    CartInventoryId that = (CartInventoryId) o;
    return Objects.equals(getCartId(), that.getCartId()) &&
        Objects.equals(getInventorySku(), that.getInventorySku());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCartId(), getInventorySku());
  }
}
