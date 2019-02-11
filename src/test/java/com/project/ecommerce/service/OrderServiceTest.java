package com.project.ecommerce.service;

import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {
  @InjectMocks
  private OrderService orderService;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private OrderInventoryService orderInventoryService;

  @Test(expected = CustomerNotFoundException.class)
  public void shouldThrowExcIfCustomerNotFound() throws CustomerNotFoundException {
    orderService.createOrder(Arrays.asList());
  }

  @Test
  public void shouldCreateOrder() throws CustomerNotFoundException {
    CartInventory cartInventory1 = new CartInventory();
    CartInventory cartInventory2 = new CartInventory();
    Customer customer = new Customer();
    Cart cart = new Cart(customer, Arrays.asList(cartInventory1, cartInventory2));
    cartInventory1.setCart(cart);
    cartInventory1.setCart(cart);
    Order order = mock(Order.class);
    when(orderRepository.save(any())).thenReturn(order);
    Order returnedOrder = orderService.createOrder(Arrays.asList(cartInventory1, cartInventory2));
    assertNotNull(returnedOrder);
  }

}