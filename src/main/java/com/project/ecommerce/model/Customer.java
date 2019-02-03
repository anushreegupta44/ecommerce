package com.project.ecommerce.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String address;
  private String phone;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
  private List<CartItem> cartItems;

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

  public List<CartItem> getCartItems() {
    return cartItems;
  }

  public void setCartItems(List<CartItem> cartItems) {
    this.cartItems = cartItems;
  }
}
