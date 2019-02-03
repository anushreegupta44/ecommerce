package com.project.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@IdClass(CartItemKey.class)
public class CartItem {

  @Id
  @ManyToOne
  private Customer customer;

  @Id
  @OneToOne
  private Inventory item;

  public CartItem() {
  }

  public CartItem(Customer customer, Inventory item) {
    this.customer = customer;
    this.item = item;
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
}
