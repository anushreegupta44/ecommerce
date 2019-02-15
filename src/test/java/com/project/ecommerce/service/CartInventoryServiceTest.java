package com.project.ecommerce.service;

import com.project.ecommerce.exception.InventoryAlreadyInCartException;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.CartInventoryRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartInventoryServiceTest {
  @InjectMocks
  private CartInventoryService cartInventoryService;

  @Mock
  private CartInventoryRepository cartInventoryRepository;

  @Mock
  private InventoryService inventoryService;

  @Test
  public void shouldSaveCartInventory() throws InventoryAlreadyInCartException {
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    cartInventoryService.mapCartToInventory(cart, inventory);
    verify(cartInventoryRepository).save(any(CartInventory.class));
  }

  @Test
  public void shouldDeleteCartInventoryMapping() throws InventoryNotFoundException {
    Cart cart = mock(Cart.class);
    Product product = mock(Product.class);
    CartInventory cartInventory1 = mock(CartInventory.class);
    CartInventory cartInventory2 = mock(CartInventory.class);
    CartInventoryService spyCartInventoryService = spy(cartInventoryService);
    when(spyCartInventoryService.getAllProductInventoriesInCart(cart.getId(), product.getId())).thenReturn(Arrays.asList(cartInventory1, cartInventory2));
    spyCartInventoryService.deleteCartInventory(cart.getId(), product.getId());
    verify(cartInventoryRepository).delete(cartInventory1);
  }

  @Test(expected = InventoryNotFoundException.class)
  public void shouldThrowExceptionIfCartInventoryDoesNotExist() throws InventoryNotFoundException {
    Cart cart = mock(Cart.class);
    Product product = mock(Product.class);
    CartInventoryService spyCartInventoryService = spy(cartInventoryService);
    when(spyCartInventoryService.getAllProductInventoriesInCart(cart.getId(), product.getId())).thenReturn(Arrays.asList());
    spyCartInventoryService.deleteCartInventory(cart.getId(), product.getId());
  }

  @Test
  public void shouldMarkInventoryAvailableIfNoCartInventoryExists() {
    Inventory inventory = mock(Inventory.class);
    CartInventoryService spyCartInventoryService = spy(cartInventoryService);
    doReturn(false).when(spyCartInventoryService).isInventoryInOtherCart(inventory);
    spyCartInventoryService.markStatusForCartInventory(inventory);
    verify(inventoryService).markInventoryWithStatus(inventory, InventoryStatus.AVAILABLE);
  }

  @Test
  public void shouldNotMarkInventoryAvailableIfNoCartInventoryExists() {
    Inventory inventory = mock(Inventory.class);
    CartInventoryService spyCartInventoryService = spy(cartInventoryService);
    doReturn(true).when(spyCartInventoryService).isInventoryInOtherCart(inventory);
    spyCartInventoryService.markStatusForCartInventory(inventory);
    verify(inventoryService, never()).markInventoryWithStatus(inventory, InventoryStatus.AVAILABLE);
  }

  @Test
  public void shouldReturnTrueIfInventoryExistsInTwoCarts() {
    CartInventory cartInventory1 = mock(CartInventory.class);
    CartInventory cartInventory2 = mock(CartInventory.class);
    Inventory inventory = mock(Inventory.class);
    when(cartInventoryRepository.findCartInventoriesByInventory(inventory)).thenReturn(Arrays.asList(cartInventory1, cartInventory2));
    assertTrue(cartInventoryService.isInventoryInOtherCart(inventory));
  }

  @Test
  public void shouldReturnFalseIfInventoryExistsInOneCart() {
    CartInventory cartInventory1 = mock(CartInventory.class);
    Inventory inventory = mock(Inventory.class);
    when(cartInventoryRepository.findCartInventoriesByInventory(inventory)).thenReturn(Arrays.asList(cartInventory1));
    assertFalse(cartInventoryService.isInventoryInOtherCart(inventory));
  }

  @Test
  public void shouldReturnFalseIfInventoryExistsInNoCart() {
    Inventory inventory = mock(Inventory.class);
    when(cartInventoryRepository.findCartInventoriesByInventory(inventory)).thenReturn(null);
    assertFalse(cartInventoryService.isInventoryInOtherCart(inventory));
  }

}