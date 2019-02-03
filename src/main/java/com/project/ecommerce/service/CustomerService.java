package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerDto;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public Customer createCustomerDetails(CustomerDto customerDto) {
    Customer customer = new Customer(customerDto.getName(), customerDto.getAddress(), customerDto.getPhone());
    Customer savedCustomer = customerRepository.save(customer);
    return savedCustomer;
  }
}
