package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderAddressDto;
import com.project.ecommerce.dto.OrderDetail;
import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.exception.OrderNotFoundException;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    //save all pulled out cart inventories to order inventories
    orderInventoryService.saveListOfOrderInventories(order, inventoriesInCart);
    //save order details
    return saveOrderTotal(order);
  }

  public OrderDetails getOrderDetails(Integer orderId) throws OrderNotFoundException {
    Order order = getOrderById(orderId);
    OrderDetails orderDetails = calculateOrderTotal(orderId);
    orderDetails.setBillingAddress(order.getBillingAddress());
    orderDetails.setShippingAddress(order.getShippingAddress());
    return orderDetails;
  }

  public OrderDetails calculateOrderTotal(Integer orderId) {
    List<OrderDetail> orderDetails = orderRepository.findOrderDetails(orderId);
    if (isNull(orderDetails) || orderDetails.size() == 0) {
      throw new OrderNotFoundException();
    }
    Long totalPrice = 0l;
    for (OrderDetail od :
        orderDetails) {
      totalPrice += od.getPricePerUnit() * od.getCount();
    }
    //assuming total taxes are always 12% of total price
    Long totalTaxes = Math.multiplyExact((long) 0.12, totalPrice);
    return new OrderDetails(orderDetails, totalTaxes, totalPrice, null, null);
  }

  public Order saveOrderTotal(Order createdOrder) throws OrderNotFoundException {
    //get total price and total taxes for order
    OrderDetails orderDetails = calculateOrderTotal(createdOrder.getId());
    createdOrder.setTotalPrice(orderDetails.getTotalPrice());
    createdOrder.setTotalTaxes(orderDetails.getTotalTaxes());
    //save order entity
    return orderRepository.save(createdOrder);
  }

  public Order getOrderById(Integer orderId) throws OrderNotFoundException {
    return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
  }

  public Order addAddressToOrder(Integer orderId, OrderAddressDto orderAddressDto) throws OrderNotFoundException {
    Order savedOrder = getOrderById(orderId);
    List<Address> addresses = getValidAddressForCustomer(savedOrder.getCustomer(), orderAddressDto);
    if (!isNull(addresses) && addresses.size() > 0) {
      addresses.stream().forEach(address -> {
        if (address.getType().equals(AddressType.BILLING)) {
          savedOrder.setBillingAddress(address);
        } else
          savedOrder.setShippingAddress(address);
      });
      return orderRepository.save(savedOrder);
    }
    return savedOrder;
  }

  public List<Address> getValidAddressForCustomer(Customer customer, OrderAddressDto orderAddressDto) {
    return customer.getAddresses().stream()
        .filter(address -> address.equals(orderAddressDto.getBillingAddress())
            || address.equals(orderAddressDto.getShippingAddress()))
        .collect(Collectors.toList());
  }
}
