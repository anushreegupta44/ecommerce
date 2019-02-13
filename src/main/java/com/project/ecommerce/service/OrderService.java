package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderDetail;
import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.exception.OrderNotFoundException;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

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

  public OrderDetails getOrderDetails(Integer orderId) throws OrderNotFoundException {
    List<OrderDetail> orderDetails = orderRepository.findOrderDetails(orderId);
    if (isNull(orderDetails) || orderDetails.size() == 0) {
      throw new OrderNotFoundException();
    }
    Long totalPrice = Long.valueOf(0);
    for (OrderDetail od :
        orderDetails) {
      totalPrice += od.getPricePerUnit() * od.getCount();
    }
    //assuming total taxes are always 12% of total price
    Long totalTaxes = Math.multiplyExact((long) 0.12, totalPrice);
    return new OrderDetails(orderDetails, totalTaxes, totalPrice);
  }

  public Order editOrderDetails(Order createdOrder) throws OrderNotFoundException {
    //get total price and total taxes for order
    OrderDetails orderDetails = getOrderDetails(createdOrder.getId());
    createdOrder.setTotalPrice(orderDetails.getTotalPrice());
    createdOrder.setTotalTaxes(orderDetails.getTotalTaxes());
    //save order entity
    return orderRepository.save(createdOrder);
  }
}
