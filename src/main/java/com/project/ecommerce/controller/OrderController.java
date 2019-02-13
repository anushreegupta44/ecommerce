package com.project.ecommerce.controller;

import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.exception.OrderNotFoundException;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDetails> getOrderDetails(@PathVariable("orderId") Integer orderId) throws OrderNotFoundException {
    return new ResponseEntity(orderService.getOrderDetails(orderId), HttpStatus.OK);
  }
}
