package com.project.ecommerce.service;

import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CartService cartService;

  @Transactional
  public Customer createCustomerDetails(Customer customer) {
    Customer savedCustomer = customerRepository.save(customer);
    cartService.createCartForUser(savedCustomer);
    return savedCustomer;
  }

  public Customer getCustomerDetails(Integer customerId) throws CustomerNotFoundException {
    return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException());
  }

  public void remove(Integer customerId) throws CustomerNotFoundException {
    this.getCustomerDetails(customerId);
    cartService.deleteUserCart(customerId);
    customerRepository.deleteById(customerId);
  }

  public ValidationResponse validateCustomerExists(Integer customerId) {
    Optional<Customer> customer = customerRepository.findById(customerId);
    if (!customer.isPresent()) {
      return new ValidationResponse(false, new HashMap<String, String>() {{
        put("customer", "customer.does.not.exist");
      }});
    } else return new ValidationResponse(true, null);

  }

}
