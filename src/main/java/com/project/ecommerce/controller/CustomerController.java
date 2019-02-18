package com.project.ecommerce.controller;

import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping("/customers/{id}")
  public ResponseEntity getCustomerDetails(@PathVariable("id") Integer customerId) throws CustomerNotFoundException {
    return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerDetails(customerId));
  }

  @PostMapping("/customers")
  public ResponseEntity<Customer> createCustomerDetails(@RequestBody @Valid Customer customer) {
    Customer savedCustomer = customerService.createCustomerDetails(customer);
    return ResponseEntity.status(HttpStatus.OK).body(savedCustomer);
  }

  @DeleteMapping("/customers/{id}")
  public ResponseEntity deleteCustomerDetails(@PathVariable("id") Integer customerId) throws CustomerNotFoundException {
    customerService.remove(customerId);
    return noContent().build();
  }


}
