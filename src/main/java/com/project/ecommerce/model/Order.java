package com.project.ecommerce.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  private Customer customer;

  @OneToMany
  @JoinTable(name="order_inventory")
  private List<Inventory> inventories;

  public Order() {
  }

  public Order(Customer customer, List<Inventory> inventories) {
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

  public List<Inventory> getInventories() {
    return inventories;
  }

  public void setInventories(List<Inventory> inventories) {
    this.inventories = inventories;
  }
}
