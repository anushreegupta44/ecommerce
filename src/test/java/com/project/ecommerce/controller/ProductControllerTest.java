package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
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

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {EcommerceApplication.class})

public class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void shouldGetProduct() throws Exception {
    Product product = new Product("product", "description of product", null);
    when(productService.getProductById(1)).thenReturn(product);
    mockMvc.perform(
        get("/products/1")
    ).andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("product")))
        .andExpect(jsonPath("$.description", is("description of product")));
  }

  @Test
  public void shouldThrowProductNotFoundExceptionForProductNotFoundInDb() throws Exception {
    when(productService.getProductById(2)).thenThrow(new ProductNotFoundException());
    MvcResult result = mockMvc.perform(
        get("/products/2")
    ).andExpect(status().isNotFound())
        .andReturn();
    String errorMessage = result.getResponse().getErrorMessage();
    assertThat(errorMessage, is("Product not found"));
  }

  @Test
  public void shouldNotDeleteProduct() throws Exception {
    Mockito.doThrow(new ProductNotFoundException()).when(productService).remove(2);
    mockMvc.perform(delete("/products/2")).andExpect(status().isNotFound());
  }

  @Test
  public void shouldDeleteProduct() throws Exception {
    mockMvc.perform(delete("/products/2")).andExpect(status().isNoContent());
  }

  @Test
  public void shouldUpdateProduct() throws Exception {
    Product incomingProduct = new Product("name", "description", Arrays.asList(new Category("category1")));
    String incomingProductJson = objectMapper.writeValueAsString(incomingProduct);
    when(productService.updateProduct(2, incomingProduct)).thenReturn(incomingProduct);
    mockMvc.perform(
        put("/products/2")
            .content(incomingProductJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
    ).andExpect(status().isOk());
  }

  @Test
  public void shouldThrowExceptionIfProductNotFound() throws Exception {
    Product incomingProduct = new Product("name", "description", Arrays.asList(new Category("category1")));
    String incomingProductJson = objectMapper.writeValueAsString(incomingProduct);
    when(productService.updateProduct(eq(2), any(Product.class))).thenThrow(new ProductNotFoundException());

    MvcResult result = mockMvc.perform(
        put("/products/2")
            .content(incomingProductJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
    ).andExpect(status().isNotFound()).andReturn();
    String errorMessage = result.getResponse().getErrorMessage();
    assertThat(errorMessage, is("Product not found"));

  }
}