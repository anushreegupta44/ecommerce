package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
  @Id
  @GeneratedValue
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private String phone;

  @OneToMany
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private List<Order> orders;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id")
  @NotNull
  private List<Address> addresses;

  public Customer() {
  }

  public Customer(String name, String phone, List<Order> orders, List<Address> addresses) {
    this.name = name;
    this.phone = phone;
    this.orders = orders;
    this.addresses = addresses;
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

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }
}
