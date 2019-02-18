package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.service.InventoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})
public class InventoryControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private InventoryService inventoryService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void shouldAddInventoryToProduct() throws Exception {
    Inventory inventory = new Inventory();
    when(inventoryService.addInventoryForProduct(anyString(), anyInt())).thenReturn(inventory);
    mockMvc.perform(
        post("/inventories/ID-12897654/products/2")
    ).andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionIfProductNotFound() throws Exception {
    Inventory inventory = new Inventory();
    when(inventoryService.addInventoryForProduct(anyString(), anyInt())).thenThrow(new ProductNotFoundException());
    MvcResult result = mockMvc.perform(
        post("/inventories/ID-12897654/products/2")
    ).andExpect(status().isNotFound()).andReturn();
    String errorMessage = result.getResponse().getErrorMessage();
    assertThat(errorMessage, is("Product not found"));
  }

}