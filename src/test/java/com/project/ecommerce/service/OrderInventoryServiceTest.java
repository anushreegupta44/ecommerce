package com.project.ecommerce.service;

import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.OrderInventory;
import com.project.ecommerce.repository.OrderInventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderInventoryServiceTest {
  @InjectMocks
  private OrderInventoryService orderInventoryService;

  @Mock
  private OrderInventoryRepository orderInventoryRepository;

  @Test
  public void doNotThrowExceptionOnSavingOrderInventory() {
    OrderInventory orderInventory = mock(OrderInventory.class);
    when(orderInventoryRepository.save(any(OrderInventory.class))).thenThrow(DataIntegrityViolationException.class);
    orderInventoryService.add(orderInventory);
  }
}