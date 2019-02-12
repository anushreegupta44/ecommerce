package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String address;
  private String phone;
  @OneToMany
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private List<Order> orders;

  public Customer() {
  }

  public Customer(String name, String address, String phone) {
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }
}
