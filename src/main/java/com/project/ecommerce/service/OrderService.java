package com.project.ecommerce.service;

import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderInventoryService orderInventoryService;

  //@Transactional(rollbackOn = CustomerNotFoundException.class)
  public Order createOrder(List<CartInventory> inventoriesInCart) throws CustomerNotFoundException {
    //create an order
    Order order = new Order();
    //get customer from cart inventory
    order.setCustomer(inventoriesInCart.stream().map(cartInventory -> cartInventory.getCart().getCustomer()).findAny().orElseThrow(CustomerNotFoundException::new));
    //save the order
    order = orderRepository.save(order);
    //save all pulled out cart inventories to order
    orderInventoryService.saveListOfOrderInventories(order, inventoriesInCart);
    return order;
  }
}
