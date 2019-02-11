package com.project.ecommerce.dto;

import java.util.List;

public class OrderDetails {
  List<OrderDetail> orderDetails;
  private Double totalTaxes;
  private Long totalPrice;
  private String shippingAddress;

  public OrderDetails() {
  }

  public OrderDetails(List<OrderDetail> orderDetails, Double totalTaxes, Long totalPrice) {
    this.orderDetails = orderDetails;
    this.totalTaxes = totalTaxes;
    this.totalPrice = totalPrice;
  }

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }

  public Double getTotalTaxes() {
    return totalTaxes;
  }

  public void setTotalTaxes(Double totalTaxes) {
    this.totalTaxes = totalTaxes;
  }

  public Long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Long totalPrice) {
    this.totalPrice = totalPrice;
  }
}
