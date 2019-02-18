package com.project.ecommerce.service;

import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.OrderInventory;
import com.project.ecommerce.repository.OrderInventoryRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInventoryService {
  @Autowired
  private OrderInventoryRepository orderInventoryRepository;

  public void add(OrderInventory orderInventory) {
    try {
      orderInventoryRepository.save(orderInventory);
    } catch (DataIntegrityViolationException | ConstraintViolationException e) {
      System.out.print("Inventory present in another order\n");
    }
  }

  public void saveListOfOrderInventories(Order order, List<CartInventory> inventoriesInCart) {
    inventoriesInCart.forEach(cartInventory -> {
      OrderInventory orderInventory = new OrderInventory();
      orderInventory.setOrder(order);
      orderInventory.setInventory(cartInventory.getInventory());
      add(orderInventory);
    });
  }
}
