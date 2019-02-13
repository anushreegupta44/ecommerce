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
  private AddressType addressType;

  public Address() {
  }

  public Address(String address, AddressType addressType) {
    this.address = address;
    this.addressType = addressType;
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

  public AddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

}
