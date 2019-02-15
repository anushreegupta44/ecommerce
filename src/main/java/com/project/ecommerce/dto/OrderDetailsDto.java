package com.project.ecommerce.dto;

import com.project.ecommerce.model.Address;
import com.project.ecommerce.model.PaymentMode;

public class OrderDetailsDto {
  private Address shippingAddress;
  private Address billingAddress;
  private PaymentMode paymentMode;

  public OrderDetailsDto() {
  }

  public OrderDetailsDto(Address shippingAddress, Address billingAddress, PaymentMode paymentMode) {
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
    this.paymentMode = paymentMode;
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

  public PaymentMode getPaymentMode() {
    return paymentMode;
  }

  public void setPaymentMode(PaymentMode paymentMode) {
    this.paymentMode = paymentMode;
  }
}
