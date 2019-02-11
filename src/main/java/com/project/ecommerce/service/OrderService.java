package com.project.ecommerce.service;

import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.repository.OrderRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  public void createOrder(List<CartInventory> inventoriesInCart) throws ConstraintViolationException {
    //create an order
    Order order = new Order();
    //get customer from cart inventory
    order.setCustomer(inventoriesInCart.stream().map(cartInventory -> cartInventory.getCart().getCustomer()).findAny().get());
    //save all pulled out inventories to order
    order.setInventories(inventoriesInCart.stream().map(cartInventory -> cartInventory.getInventory()).collect(Collectors.toList()));
    //persist the order
    orderRepository.save(order);

  }
}
