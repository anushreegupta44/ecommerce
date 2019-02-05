package com.project.ecommerce.service;

import com.project.ecommerce.model.Customer;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

  @InjectMocks
  private CustomerService customerService;

  @Mock
  private CustomerRepository customerRepository;

  @Test
  public void shouldReturnValidResCustomerExistsInDb() {
    Customer customer = mock(Customer.class);
    when(customerRepository.findById(customer.getId())).thenReturn(java.util.Optional.ofNullable(customer));
    ValidationResponse res = customerService.validateCustomerExists(customer.getId());
    assertTrue(res.getValid());
  }

  @Test
  public void shouldReturnErrorIfCustomerDoesNotExistInDb() {
    Customer customer = mock(Customer.class);
    when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());
    ValidationResponse res = customerService.validateCustomerExists(customer.getId());
    assertFalse(res.getValid());
  }

}