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
//  @JsonIgnore
  private Customer customer;

  @OneToMany
  @JoinColumn(name = "ORDER_ID")
  @JsonIgnore
  private List<OrderInventory> orderInventories;

  @ManyToOne
  private Address shippingAddress;

  @ManyToOne
  private Address billingAddress;

  private Long totalPrice;

  private Long totalTaxes;

  public Order() {
  }

  public Order(Customer customer, List<OrderInventory> orderInventories) {
    this.customer = customer;
    this.orderInventories = orderInventories;
  }

  public Order(Customer customer, List<OrderInventory> orderInventories, Address shippingAddress, Address billingAddress, Long totalPrice, Long totalTaxes) {
    this.customer = customer;
    this.orderInventories = orderInventories;
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
    this.totalPrice = totalPrice;
    this.totalTaxes = totalTaxes;
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

  public Long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Long totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Long getTotalTaxes() {
    return totalTaxes;
  }

  public void setTotalTaxes(Long totalTaxes) {
    this.totalTaxes = totalTaxes;
  }
}
