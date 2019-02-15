package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "payment_mode")
public class PaymentMode {
  @Id
  @GeneratedValue
  private Integer id;

  @Enumerated(EnumType.STRING)
  private ModeOfPayment modeOfPayment;

  private String details;

  @OneToOne
  @JsonIgnore
  private Customer customer;

  public PaymentMode() {
  }

  public PaymentMode(ModeOfPayment modeOfPayment, String details, Customer customer) {
    this.modeOfPayment = modeOfPayment;
    this.details = details;
    this.customer = customer;
  }

  public ModeOfPayment getModeOfPayment() {
    return modeOfPayment;
  }

  public void setModeOfPayment(ModeOfPayment modeOfPayment) {
    this.modeOfPayment = modeOfPayment;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
