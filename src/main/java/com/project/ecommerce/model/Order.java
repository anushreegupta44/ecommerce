package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue
  private Integer id;

  @ManyToOne
  @JsonIgnore
  private Customer customer;

  @OneToMany
  @JoinColumn(name = "ORDER_ID")
  @JsonIgnore
  private List<OrderInventory> orderInventories;

  public Order() {
  }

  public Order(Customer customer, List<OrderInventory> orderInventories) {
    this.customer = customer;
    this.orderInventories = orderInventories;
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

  public List<OrderInventory> getOrderInventories() {
    return orderInventories;
  }

  public void setOrderInventories(List<OrderInventory> orderInventories) {
    this.orderInventories = orderInventories;
  }
}
