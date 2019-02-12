package com.project.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Inventories")
public class Inventory {
  @Id
  private String sku;

  @ManyToOne
  private Product product;

  @Enumerated(EnumType.STRING)
  private InventoryStatus status;

  @OneToMany
  @JoinColumn(name = "INVENTORY_SKU")
  private List<CartInventory> carts;

  public Inventory() {
  }

  public Inventory(String sku, Product product, InventoryStatus status, List<CartInventory> carts) {
    this.sku = sku;
    this.product = product;
    this.status = status;
    this.carts = carts;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public InventoryStatus getStatus() {
    return status;
  }

  public void setStatus(InventoryStatus status) {
    this.status = status;
  }

  public List<CartInventory> getCarts() {
    return carts;
  }

  public void setCarts(List<CartInventory> carts) {
    this.carts = carts;
  }
}
