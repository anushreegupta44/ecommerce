package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})
public class CartControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private CartService cartService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void shouldAddProductToCart() throws Exception {
    mockMvc.perform(
        post("/cart/2/product/2")
    ).andExpect(status().isOk());
  }

  @Test
  public void shouldNotAddProductToCart() throws Exception {
    Mockito.doThrow(new InventoryNotFoundException()).when(cartService).addProductToCart(anyInt(), anyInt());
    MvcResult result = mockMvc.perform(
        post("/cart/2/product/2")
    ).andExpect(status().isNotFound())
        .andReturn();
    String errorMessage = result.getResponse().getErrorMessage();
    assertThat(errorMessage, is("Inventory empty for product"));
  }

  @Test
  public void shouldDeleteProductInCart() throws Exception {
    mockMvc.perform(
        delete("/cart/2/product/2")
    ).andExpect(status().isNoContent());
  }

}