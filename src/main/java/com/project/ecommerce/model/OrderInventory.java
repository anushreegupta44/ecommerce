package com.project.ecommerce.model;

import javax.persistence.*;

@Entity
@Table(name = "Order_Inventory",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"INVENTORY_SKU"})})
//have a third table to hold the one to many mapping of order and inventory
//Not using the build in functionality to map a list of inventories to order because have to handle the failure scenario for an inventory already checked out in another order
public class OrderInventory {
  @EmbeddedId
  private OrderInventoryId orderInventoryId = new OrderInventoryId();

  @ManyToOne
  @JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)
  @MapsId("orderId")
  private Order order;

  @OneToOne
  @MapsId("inventorySku")
  private Inventory inventory;

  public OrderInventory() {
  }

  public OrderInventory(OrderInventoryId orderInventoryId, Order order, Inventory inventory) {
    this.orderInventoryId = orderInventoryId;
    this.order = order;
    this.inventory = inventory;
  }

  public OrderInventoryId getOrderInventoryId() {
    return orderInventoryId;
  }

  public void setOrderInventoryId(OrderInventoryId orderInventoryId) {
    this.orderInventoryId = orderInventoryId;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }
}
