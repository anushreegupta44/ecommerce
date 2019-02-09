package com.project.ecommerce.service;

import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.repository.CartInventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartInventoryServiceTest {
  @InjectMocks
  private CartInventoryService cartInventoryService;

  @Mock
  private CartInventoryRepository cartInventoryRepository;

  @Test
  public void shouldSaveCartInventory() {
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    CartInventory cartInventory = new CartInventory(cart, inventory);
    cartInventoryService.mapCartToInventory(cart, inventory);
    verify(cartInventoryRepository).save(cartInventory);
  }

}