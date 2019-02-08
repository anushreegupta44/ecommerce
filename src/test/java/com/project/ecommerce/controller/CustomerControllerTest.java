package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.exception.CustomerNotFoundException;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})
public class CustomerControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void shouldGetCustomer() throws Exception {
    Customer customer = new Customer("customer", "address", "987654321");
    when(customerService.getCustomerDetails(1)).thenReturn(customer);
    mockMvc.perform(
        get("/customers/1")
    ).andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("customer")))
        .andExpect(jsonPath("$.address", is("address")))
        .andExpect(jsonPath("$.phone", is("987654321")));
  }

  @Test
  public void shouldThrowExceptionIfCustomerNotFoundInDbGet() throws Exception {
    when(customerService.getCustomerDetails(2)).thenThrow(new CustomerNotFoundException());
    MvcResult result = mockMvc.perform(
        get("/customers/2")
    ).andExpect(status().isNotFound())
        .andReturn();
    String errorMessage = result.getResponse().getErrorMessage();
    assertThat(errorMessage, is("Customer not found"));
  }

  @Test
  public void shouldCreateCustomer() throws Exception {
    Customer incomingCustomer = new Customer("name", "address", "9899999999");
    String incomingCustomerJson = objectMapper.writeValueAsString(incomingCustomer);
    when(customerService.createCustomerDetails(incomingCustomer)).thenReturn(incomingCustomer);
    mockMvc.perform(
        post("/customers")
            .content(incomingCustomerJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
    ).andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionDeleteCustomerIfCustomerNotInDb() throws Exception {
    Mockito.doThrow(new CustomerNotFoundException()).when(customerService).remove(2);
    mockMvc.perform(delete("/customers/2")).andExpect(status().isNotFound());
  }

  @Test
  public void shouldDeleteCustomer() throws Exception {
    mockMvc.perform(delete("/customers/2")).andExpect(status().isNoContent());
  }

}