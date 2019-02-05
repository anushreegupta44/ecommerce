package com.project.ecommerce.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cart_item")
public class CartItem {

  @EmbeddedId
  private CartItemKey cartItemKey;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
  private Customer customer;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "item_sku", nullable = false, insertable = false, updatable = false)
  private Inventory item;

  public CartItem() {
  }

  public CartItem(Customer customer, Inventory item) {
    this.customer = customer;
    this.item = item;
  }

  public CartItemKey getCartItemKey() {
    return cartItemKey;
  }

  public void setCartItemKey(CartItemKey cartItemKey) {
    this.cartItemKey = cartItemKey;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Inventory getItem() {
    return item;
  }

  public void setItem(Inventory item) {
    this.item = item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItem cartItem = (CartItem) o;
    return Objects.equals(getCustomer().getId(), cartItem.getCustomer().getId()) &&
        Objects.equals(getItem().getSku(), cartItem.getItem().getSku());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCustomer(), getItem());
  }
}
