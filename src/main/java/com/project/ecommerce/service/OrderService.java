package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderDetail;
import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.dto.OrderDetailsDto;
import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.exception.OrderNotFoundException;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderInventoryService orderInventoryService;

  public Order createOrder(List<CartInventory> inventoriesInCart) throws CustomerNotFoundException, OrderNotFoundException {
    Order order = new Order();
    order.setCustomer(inventoriesInCart.stream().map(cartInventory -> cartInventory.getCart().getCustomer()).findAny().orElseThrow(CustomerNotFoundException::new));
    order = orderRepository.save(order);
    orderInventoryService.saveListOfOrderInventories(order, inventoriesInCart);
    return saveOrderTotal(order);
  }

  public OrderDetails getOrderDetails(Integer orderId) throws OrderNotFoundException {
    Order order = getOrderById(orderId);
    OrderDetails orderDetails = calculateOrderTotal(orderId);
    orderDetails.setBillingAddress(order.getBillingAddress());
    orderDetails.setShippingAddress(order.getShippingAddress());
    orderDetails.setCustomer(order.getCustomer());
    return orderDetails;
  }

  public OrderDetails calculateOrderTotal(Integer orderId) throws OrderNotFoundException {
    List<OrderDetail> orderDetailsList = orderRepository.findOrderDetails(orderId);
    if (isNull(orderDetailsList) || orderDetailsList.size() == 0) {
      throw new OrderNotFoundException();
    }
    OrderDetails orderDetails = new OrderDetails(orderDetailsList);
    orderDetails.setTotalPrice();
    orderDetails.setTotalTaxes();
    return orderDetails;
  }

  public Order saveOrderTotal(Order createdOrder) throws OrderNotFoundException {
    OrderDetails orderDetails = calculateOrderTotal(createdOrder.getId());
    createdOrder.setTotalPrice(orderDetails.getTotalPrice());
    createdOrder.setTotalTaxes(orderDetails.getTotalTaxes());
    return orderRepository.save(createdOrder);
  }

  public Order getOrderById(Integer orderId) throws OrderNotFoundException {
    return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
  }

  public Order addOrderDetails(Integer orderId, OrderDetailsDto orderDetailsDto) throws OrderNotFoundException {
    Order order = getOrderById(orderId);
    order = addAddressToOrder(order, orderDetailsDto);
    order = addPaymentMethodToOrder(order, orderDetailsDto);
    return orderRepository.save(order);
  }

  public Order addPaymentMethodToOrder(Order order, OrderDetailsDto orderDetailsDto) {
    Optional<PaymentMode> validPaymentMode = getValidPaymentMode(order, orderDetailsDto.getPaymentMode());
    validPaymentMode.ifPresent(order::setPaymentMode);
    return order;
  }

  public Optional<PaymentMode> getValidPaymentMode(Order order, PaymentMode paymentModeDto) {
    return order.getCustomer().getPaymentModes().stream()
        .filter(paymentMode -> paymentMode.getId().equals(paymentModeDto.getId()))
        .findAny();
  }

  public Order addAddressToOrder(Order order, OrderDetailsDto orderDetailsDto) throws OrderNotFoundException {
    List<Address> addresses = getValidAddressForCustomer(order.getCustomer(), orderDetailsDto);
    if (!isNull(addresses) && addresses.size() > 0) {
      addresses.forEach(address -> {
        if (address.getType().equals(AddressType.BILLING)) {
          order.setBillingAddress(address);
        } else
          order.setShippingAddress(address);
      });
    }
    return order;
  }

  public List<Address> getValidAddressForCustomer(Customer customer, OrderDetailsDto orderDetailsDto) {
    return customer.getAddresses().stream()
        .filter(address -> address.equals(orderDetailsDto.getBillingAddress())
            || address.equals(orderDetailsDto.getShippingAddress()))
        .collect(Collectors.toList());
  }
}
