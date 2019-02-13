package com.project.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue
  private Integer id;

  private String address;

  @Enumerated(EnumType.STRING)
  private AddressType type;

  public Address() {
  }

  public Address(String address, AddressType type) {
    this.address = address;
    this.type = type;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public AddressType getType() {
    return type;
  }

  public void setType(AddressType type) {
    this.type = type;
  }

}
