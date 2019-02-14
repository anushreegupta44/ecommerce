package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderAddressDto;
import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.exception.OrderNotFoundException;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.OrderRepository;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    Cart cart = new Cart();
    Customer customer = new Customer();
    cart.setCustomer(customer);
    cartInventory1.setCart(cart);
    Order order = new Order();
    OrderService spy = spy(orderService);
    doReturn(order).when(orderRepository).save(any(Order.class));
    doReturn(order).when(spy).saveOrderTotal(any(Order.class));
    spy.createOrder(Arrays.asList(cartInventory1));
  }

  @Test
  public void shouldCreateOrderDetails() throws OrderNotFoundException {
    OrderService spyOrderService = spy(orderService);
    Order order = new Order();
    doReturn(order).when(spyOrderService).getOrderById(anyInt());
    doReturn(new OrderDetails(null, null, 0l, 12l, new Address(), new Address())).when(spyOrderService).calculateOrderTotal(anyInt());
    OrderDetails orderDetails = spyOrderService.getOrderDetails(2);
    assertEquals((Long) orderDetails.getTotalPrice(), (Long) 12l);
  }

  @Test(expected = OrderNotFoundException.class)
  public void shouldThrowExpIfNoOrderDetailExists() throws OrderNotFoundException {
    when(orderRepository.findOrderDetails(anyInt())).thenReturn(Arrays.asList());
    OrderDetails orderDetails = orderService.getOrderDetails(2);
  }

  @Test
  public void shouldPopulateOrderDetails() {
    Order order = new Order();
    order.setBillingAddress(new Address());
    OrderDetails orderDetails = new OrderDetails(null, null, 0l, 9l, null, null);
    OrderService spyOrderService = spy(orderService);
    doReturn(order).when(spyOrderService).getOrderById(anyInt());
    doReturn(orderDetails).when(spyOrderService).calculateOrderTotal(anyInt());
    spyOrderService.getOrderDetails(2);
    assertNotNull(orderDetails.getBillingAddress());
    assertNull(orderDetails.getShippingAddress());

  }

  @Test
  public void shouldNotReturnAnyAddress() {
    Address billingAddress = new Address("123", AddressType.BILLING);
    billingAddress.setId(1);
    Address shippingAddress = new Address("789", AddressType.SHIPPING);
    shippingAddress.setId(2);
    Address invalidBillingAddress = new Address("123", AddressType.BILLING);
    invalidBillingAddress.setId(3);
    Address invalidShippingAddress = new Address("789", AddressType.SHIPPING);
    invalidShippingAddress.setId(4);
    Customer customer = new Customer("name", "989720432", null, Arrays.asList(billingAddress, shippingAddress));
    OrderAddressDto orderAddressDto = new OrderAddressDto(invalidShippingAddress, invalidBillingAddress);
    List<Address> addressList = orderService.getValidAddressForCustomer(customer, orderAddressDto);
    MatcherAssert.assertThat(addressList.size(), is(0));
  }

  @Test
  public void shouldFilterAdressesThatBelongToUserFromDto() {
    Address billingAddress = new Address("123", AddressType.BILLING);
    billingAddress.setId(1);
    Address shippingAddress = new Address("789", AddressType.SHIPPING);
    shippingAddress.setId(2);
    Address invalidBillingAddress = new Address("123", AddressType.BILLING);
    invalidBillingAddress.setId(3);
    Address invalidShippingAddress = new Address("789", AddressType.SHIPPING);
    invalidShippingAddress.setId(4);
    Customer customer = new Customer("name", "989720432", null, Arrays.asList(billingAddress, shippingAddress));
    OrderAddressDto orderAddressDto = new OrderAddressDto(billingAddress, invalidBillingAddress);
    List<Address> addressList = orderService.getValidAddressForCustomer(customer, orderAddressDto);
    MatcherAssert.assertThat(addressList.size(), is(1));
  }

  @Test
  public void shouldSaveAddressesIfIncomingAdressIsValid() {
    Order order = new Order();
    Customer customer = new Customer();
    order.setCustomer(customer);
    Address shippingAddress = new Address("address", AddressType.SHIPPING);
    OrderAddressDto orderAddressDto = new OrderAddressDto();
    OrderService spyOrderService = spy(orderService);
    doReturn(order).when(spyOrderService).getOrderById(anyInt());
    doReturn(Arrays.asList(shippingAddress)).when(spyOrderService)
        .getValidAddressForCustomer(any(Customer.class), any(OrderAddressDto.class));
    spyOrderService.addAddressToOrder(2, orderAddressDto);
    verify(orderRepository).save(order);
  }

  @Test
  public void shouldSaveAddressesOnceIfIncomingAdressIsValid() {
    Order order = new Order();
    Customer customer = new Customer();
    order.setCustomer(customer);
    Address shippingAddress = new Address("address", AddressType.SHIPPING);
    Address billingAddress = new Address("address", AddressType.BILLING);
    OrderAddressDto orderAddressDto = new OrderAddressDto();
    OrderService spyOrderService = spy(orderService);
    doReturn(order).when(spyOrderService).getOrderById(anyInt());
    doReturn(Arrays.asList(shippingAddress, billingAddress)).when(spyOrderService)
        .getValidAddressForCustomer(any(Customer.class), any(OrderAddressDto.class));
    spyOrderService.addAddressToOrder(2, orderAddressDto);
    verify(orderRepository).save(order);
  }

}