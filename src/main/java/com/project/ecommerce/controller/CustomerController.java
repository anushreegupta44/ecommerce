package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CustomerDto;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.service.CustomerService;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  //I do not create an endpoint for getting all customers because a user will only be able to get their own information and hence details only about themselves
  @GetMapping("/customer")
  public ResponseEntity getCustomerDetails() {
    return null;
  }

  @PostMapping()
  public ResponseEntity createCustomerDetails(@RequestBody @Valid @NotNull CustomerDto customerDto) {
    Customer customer = customerService.createCustomerDetails(customerDto);
    if (customer != null) {
      return created(URI.create("/customer/" + customer.getId())).build();
    } else
      return notFound().build();
  }

  @DeleteMapping("/customer/{id}")
  public ResponseEntity deleteCustomerDetails(@PathVariable("id") Integer customerId) {
    ValidationResponse res = customerService.validateCustomerExists(customerId);
    if (res.getErrors() != null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res.getErrors());
    } else {
      customerService.remove(customerId);
      return noContent().build();
    }
  }


}
