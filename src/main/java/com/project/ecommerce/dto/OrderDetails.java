package com.project.ecommerce.dto;

import com.project.ecommerce.model.Address;
import com.project.ecommerce.model.Customer;

import java.util.List;

public class OrderDetails {
  List<OrderDetail> orderDetails;
  private Customer customer;
  private Long totalTaxes;
  private Long totalPrice;
  private Address shippingAddress;
  private Address billingAddress;

  public OrderDetails() {
  }

  public OrderDetails(List<OrderDetail> orderDetails, Customer customer, Long totalTaxes, Long totalPrice, Address shippingAddress, Address billingAddress) {
    this.orderDetails = orderDetails;
    this.customer = customer;
    this.totalTaxes = totalTaxes;
    this.totalPrice = totalPrice;
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
  }

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }

  public Long getTotalTaxes() {
    return totalTaxes;
  }

  public void setTotalTaxes(Long totalTaxes) {
    this.totalTaxes = totalTaxes;
  }

  public Long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Long totalPrice) {
    this.totalPrice = totalPrice;
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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}
