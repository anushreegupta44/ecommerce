package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.dto.OrderDetailsDto;
import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.exception.OrderNotFoundException;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.project.ecommerce.model.ModeOfPayment.UPI;
import static junit.framework.TestCase.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    Order order = mock(Order.class);
    doReturn(order).when(spyOrderService).getOrderById(anyInt());
    doReturn(new OrderDetails(null, null, 0l, 12l, new Address(), new Address())).when(spyOrderService).calculateOrderTotal(anyInt());
    OrderDetails orderDetails = spyOrderService.getOrderDetails(2);
    assertEquals((Long) orderDetails.getTotalPrice(), (Long) 12l);
  }

  @Test(expected = OrderNotFoundException.class)
  public void shouldThrowExpIfNoOrderDetailExists() throws OrderNotFoundException {
    when(orderRepository.findOrderDetails(anyInt())).thenReturn(Arrays.asList());
    orderService.getOrderDetails(2);
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
    Customer customer = new Customer("name", "989720432", null, Arrays.asList(billingAddress, shippingAddress), null);
    OrderDetailsDto orderDetailsDto = new OrderDetailsDto(invalidShippingAddress, invalidBillingAddress, null);
    List<Address> addressList = orderService.getValidAddressForCustomer(customer, orderDetailsDto);
    assertThat(addressList.size(), is(0));
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
    Customer customer = new Customer("name", "989720432", null, Arrays.asList(billingAddress, shippingAddress), null);
    OrderDetailsDto orderDetailsDto = new OrderDetailsDto(billingAddress, invalidBillingAddress, null);
    List<Address> addressList = orderService.getValidAddressForCustomer(customer, orderDetailsDto);
    assertThat(addressList.size(), is(1));
  }

  @Test
  public void shouldSaveAddressesIfIncomingAdressIsValid() {
    Order order = new Order();
    Customer customer = new Customer();
    order.setCustomer(customer);
    Address shippingAddress = new Address("address", AddressType.SHIPPING);
    OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
    OrderService spyOrderService = spy(orderService);
    doReturn(Collections.singletonList(shippingAddress)).when(spyOrderService)
        .getValidAddressForCustomer(any(Customer.class), any(OrderDetailsDto.class));
    spyOrderService.addAddressToOrder(order, orderDetailsDto);
    assertThat(order.getShippingAddress(), is(shippingAddress));
  }

  @Test
  public void shouldAddPaymentMethodToOrder() {
    Order order = new Order();
    Customer customer = new Customer();
    PaymentMode paymentMode = new PaymentMode(UPI, "", customer);
    OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
    orderDetailsDto.setPaymentMode(paymentMode);
    order.setCustomer(customer);
    order.setPaymentMode(paymentMode);
    OrderService spyOrderService = spy(orderService);
    doReturn(Optional.of(paymentMode)).when(spyOrderService).getValidPaymentMode(any(Order.class), any(PaymentMode.class));
    spyOrderService.addPaymentMethodToOrder(order, orderDetailsDto);
    assertThat(order.getPaymentMode(), is(paymentMode));
  }

  @Test
  public void shouldSaveOrder() {
    Order order = new Order();
    OrderService spyOrderService = spy(orderService);
    doReturn(order).when(spyOrderService).getOrderById(anyInt());
    doReturn(order).when(spyOrderService).addAddressToOrder(any(Order.class), any(OrderDetailsDto.class));
    doReturn(order).when(spyOrderService).addPaymentMethodToOrder(any(Order.class), any(OrderDetailsDto.class));
    spyOrderService.addOrderDetails(2, new OrderDetailsDto());
    verify(orderRepository).save(order);
  }

  @Test
  public void shouldReturnEmptyOptionalIfNoValidPaymentMode() {
    Order order = new Order();
    Customer customer = new Customer();
    PaymentMode paymentMode = new PaymentMode();
    paymentMode.setId(1);
    customer.setPaymentModes(Collections.singletonList(paymentMode));
    order.setCustomer(customer);
    PaymentMode paymentModeDto = new PaymentMode();
    paymentModeDto.setId(2);
    Optional<PaymentMode> paymentMode1 = orderService.getValidPaymentMode(order, paymentModeDto);
    assertFalse(paymentMode1.isPresent());
  }

  @Test
  public void shouldReturnOptionalIfValidPaymentMode() {
    Order order = new Order();
    Customer customer = new Customer();
    PaymentMode paymentMode = new PaymentMode();
    paymentMode.setId(1);
    customer.setPaymentModes(Collections.singletonList(paymentMode));
    order.setCustomer(customer);
    PaymentMode paymentModeDto = new PaymentMode();
    paymentModeDto.setId(1);
    Optional<PaymentMode> paymentMode1 = orderService.getValidPaymentMode(order, paymentModeDto);
    assertTrue(paymentMode1.isPresent());
  }

}