package com.project.ecommerce.controller;

import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ProductService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}