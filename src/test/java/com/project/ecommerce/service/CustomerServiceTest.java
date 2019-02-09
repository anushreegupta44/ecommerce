package com.project.ecommerce.service;

import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

  @InjectMocks
  private CustomerService customerService;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private CartService cartService;

  @Test
  public void shouldReturnCustomerWhenDetailsSavedSuccessfully() {
    Customer customer = mock(Customer.class);
    when(customerRepository.save(customer)).thenReturn(customer);
    Customer savedCustomer = customerService.createCustomerDetails(customer);
    assertThat(savedCustomer, is(customer));
    verify(cartService).createCartForUser(customer);
  }

  @Test
  public void shouldGetCustomerDetailsGetCustomerById() throws CustomerNotFoundException {
    Customer customer = mock(Customer.class);
    when(customerRepository.findById(customer.getId())).thenReturn(java.util.Optional.ofNullable(customer));
    Customer recievedCustomer = customerService.getCustomerDetails(customer.getId());
    assertThat(recievedCustomer, is(customer));
  }

  @Test(expected = CustomerNotFoundException.class)
  public void shouldThrowErrorIfCustomerDoesNotExist() throws CustomerNotFoundException {
    Customer customer = mock(Customer.class);
    when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());
    customerService.getCustomerDetails(customer.getId());
  }

  @Test
  public void shouldDeleteCustomerIfCustomerFoundInDb() throws CustomerNotFoundException {
    Customer customer = mock(Customer.class);
    CustomerService spyCustomerService = Mockito.spy(customerService);
    doReturn(customer).when(spyCustomerService).getCustomerDetails(Mockito.anyInt());
    spyCustomerService.remove(customer.getId());
    verify(customerRepository).deleteById(customer.getId());
  }

  @Test(expected = CustomerNotFoundException.class)
  public void shouldThrowExceptionCustomerIfCustomerNotFoundInDb() throws CustomerNotFoundException {
    Customer customer = mock(Customer.class);
    CustomerService spyCustomerService = Mockito.spy(customerService);
    doThrow(new CustomerNotFoundException()).when(spyCustomerService).getCustomerDetails(Mockito.anyInt());
    spyCustomerService.remove(customer.getId());
  }

}