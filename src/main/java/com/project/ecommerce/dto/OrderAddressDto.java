package com.project.ecommerce.dto;

import com.project.ecommerce.model.Address;

public class OrderAddressDto {
  private Address shippingAddress;
  private Address billingAddress;

  public OrderAddressDto() {
  }

  public OrderAddressDto(Address shippingAddress, Address billingAddress) {
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }
}
