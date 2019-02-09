package com.project.ecommerce.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cart_inventory")
//have a third table to hold the many to many mapping of cart and inventory
public class CartInventory {
  @EmbeddedId
  private CartInventoryId id = new CartInventoryId();

  @ManyToOne
  @JoinColumn(name = "CART_ID", nullable = false, insertable = false, updatable = false)
  @MapsId("cartId")
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "INVENTORY_SKU", nullable = false, insertable = false, updatable = false)
  @MapsId("inventorySku")
  private Inventory inventory;

  private Date createdOn = new Date();

  public CartInventory() {
  }

  public CartInventory(Cart cart, Inventory inventory) {
    this.cart = cart;
    this.inventory = inventory;
  }

  public CartInventory(CartInventoryId id, Cart cart, Inventory inventory, Date createdOn) {
    this.id = id;
    this.cart = cart;
    this.inventory = inventory;
    this.createdOn = createdOn;
  }

  public CartInventoryId getId() {
    return id;
  }

  public void setId(CartInventoryId id) {
    this.id = id;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartInventory that = (CartInventory) o;
    return Objects.equals(getId(), that.getId()) &&
        Objects.equals(getCart(), that.getCart()) &&
        Objects.equals(getInventory(), that.getInventory()) &&
        Objects.equals(getCreatedOn(), that.getCreatedOn());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getCart(), getInventory(), getCreatedOn());
  }
}
