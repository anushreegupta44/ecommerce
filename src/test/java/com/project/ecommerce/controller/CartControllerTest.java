package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.EcommerceApplication;
import com.project.ecommerce.dto.InCartProduct;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.OrderInventory;
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

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        post("/carts/2/products/2")
    ).andExpect(status().isOk());
  }

  @Test
  public void shouldNotAddProductToCart() throws Exception {
    Mockito.doThrow(new InventoryNotFoundException()).when(cartService).addProductToCart(anyInt(), anyInt());
    MvcResult result = mockMvc.perform(
        post("/carts/2/products/2")
    ).andExpect(status().isNotFound())
        .andReturn();
    String errorMessage = result.getResponse().getErrorMessage();
    assertThat(errorMessage, is("Inventory empty for product"));
  }

  @Test
  public void shouldDeleteProductInCart() throws Exception {
    mockMvc.perform(
        delete("/carts/2/products/2")
    ).andExpect(status().isNoContent());
  }

  @Test
  public void shouldGetProductInCart() throws Exception {
    Cart cart = mock(Cart.class);
    InCartProduct inCartProduct1 = new InCartProduct(1, 2l, "product1", "description1");
    InCartProduct inCartProduct2 = new InCartProduct(2, 4l, "product2", "description2");
    when(cartService.getItemsInCart(anyInt())).thenReturn(Arrays.asList(inCartProduct1, inCartProduct2));
    MvcResult result = mockMvc.perform(
        get("/carts/2")
    ).andExpect(status().isOk())
        .andReturn();
    assertFalse(result.getResponse().getContentAsString().isEmpty());
    String content = result.getResponse().getContentAsString();
    assertThat(content, is("[{\"productId\":1,\"count\":2,\"name\":\"product1\",\"description\":\"description1\"},{\"productId\":2,\"count\":4,\"name\":\"product2\",\"description\":\"description2\"}]"));

  }

  @Test
  public void shouldCheckoutCart() throws Exception {
    Order order = new Order();
    order.setOrderInventories(Collections.singletonList(new OrderInventory()));
    order.setCustomer(new Customer("customer", "phone", null, null, null));
    when(cartService.checkoutCart(anyInt())).thenReturn(order);
    MvcResult result = mockMvc.perform(
        post("/carts/2/checkout")
    ).andExpect(status().isOk()).andReturn();
    String content = result.getResponse().getContentAsString();
    assertTrue(content.contains("id"));
  }

  @Test
  public void shouldGetCartForCustomer() throws Exception {
    Cart cart = new Cart();
    when(cartService.getCartForCustomer(anyInt())).thenReturn(cart);
    MvcResult result = mockMvc.perform(
        get("/carts/customers/2")
    ).andExpect(status().isOk()).andReturn();
    String content = result.getResponse().getContentAsString();
    System.out.print(content);
    assertTrue(content.contains("inventories"));
  }

}