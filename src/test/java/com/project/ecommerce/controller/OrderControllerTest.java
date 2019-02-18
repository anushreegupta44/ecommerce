package com.project.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ecommerce.dto.OrderDetail;
import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.dto.OrderDetailsDto;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldGetOrderDetails() throws Exception {
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setOrderDetails(Collections.singletonList(new OrderDetail()));
    when(orderService.getOrderDetails(anyInt())).thenReturn(orderDetails);
    mockMvc.perform(
        get("/orders/1")
    ).andExpect(status().isOk())
        .andExpect(jsonPath("$.orderDetails.length()", is(1)));
  }

  @Test
  public void shouldAddOrderDetails() throws Exception {
    OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
    String incomingDetailsJson = objectMapper.writeValueAsString(orderDetailsDto);
    Order order = new Order();
    when(orderService.addOrderDetails(anyInt(), any(OrderDetailsDto.class))).thenReturn(order);

    mockMvc.perform(
        post("/orders/2")
            .content(incomingDetailsJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
    ).andExpect(status().isOk());
  }
}