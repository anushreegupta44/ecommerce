package com.project.ecommerce.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue
  private Integer id;

  @OneToOne
  private Customer customer;

  @OneToMany
  @JoinColumn(name = "CART_ID")
  private List<CartInventory> inventories;

  public Cart() {
  }

  public Cart(Customer customer, List<CartInventory> inventories) {
    this.customer = customer;
    this.inventories = inventories;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public List<CartInventory> getInventories() {
    return inventories;
  }

  public void setInventories(List<CartInventory> inventories) {
    this.inventories = inventories;
  }
}
