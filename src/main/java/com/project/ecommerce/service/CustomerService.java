package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerDto;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public Customer createCustomerDetails(CustomerDto customerDto) {
    Customer customer = new Customer(customerDto.getName(), customerDto.getAddress(), customerDto.getPhone());
    Customer savedCustomer = customerRepository.save(customer);
    return savedCustomer;
  }

  public ValidationResponse validateCustomerExists(Integer customerId) {
    Optional<Customer> customer = customerRepository.findById(customerId);
    if (!customer.isPresent()) {
      return new ValidationResponse(false, new HashMap<String, String>() {{
        put("customer", "customer.does.not.exist");
      }});
    } else return new ValidationResponse(true, null);

  }

  public void remove(Integer customerId) {
    customerRepository.deleteById(customerId);
  }
}
