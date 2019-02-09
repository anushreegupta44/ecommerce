package com.project.ecommerce.service;

import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryServiceTest {

  @Mock
  private InventoryRepository inventoryRepository;

  @InjectMocks
  private InventoryService inventoryService;

  @Test
  public void giveValidResIfInventoryForProductExists() {
    Integer productIdForInventories = 1;
    List<Inventory> inventories = new ArrayList<>();
    Inventory inventory = mock(Inventory.class);
    inventories.add(inventory);
    when(inventoryRepository.findInventoriesByProduct_IdAndStatus(productIdForInventories, InventoryStatus.AVAILABLE)).thenReturn(inventories);
    ValidationResponse res = inventoryService.validateInventoryForProductExists(productIdForInventories);
    assertTrue(res.getValid());
  }

  @Test
  public void giveInvalidResIfInventoryForProductDoesNotExist() {
    Integer productIdForInventories = 1;
    List<Inventory> inventories = new ArrayList<>();
    when(inventoryRepository.findInventoriesByProduct_IdAndStatus(productIdForInventories, InventoryStatus.AVAILABLE)).thenReturn(inventories);
    ValidationResponse res = inventoryService.validateInventoryForProductExists(productIdForInventories);
    assertFalse(res.getValid());
  }

  @Test
  public void shouldMarkInventoryInCartAndSaveIt() {
    Inventory inventory = mock(Inventory.class);
    inventory.setStatus(InventoryStatus.AVAILABLE);
    inventoryService.markInventoryAsInCart(inventory);
    verify(inventoryRepository).save(inventory);
    verify(inventory).setStatus(InventoryStatus.IN_CART);
  }

  @Test
  public void givesAvailableInventoryToAdd() throws InventoryNotFoundException {
    Product product = mock(Product.class);
    Inventory availableInventory1 = new Inventory();
    availableInventory1.setStatus(InventoryStatus.AVAILABLE);
    Inventory availableInventory2 = new Inventory();
    availableInventory2.setStatus(InventoryStatus.IN_CART);
    when(inventoryRepository.findInventoriesByProduct_IdAndStatus(product.getId(), InventoryStatus.AVAILABLE)).thenReturn(Arrays.asList(availableInventory1, availableInventory2));
    Inventory inventory = inventoryService.getInventoryToAdd(product.getId());
    assert inventory.getStatus().equals(InventoryStatus.AVAILABLE);
  }

  @Test
  public void givesInCartInventoryToAddIfBothInventoriesInCart() throws InventoryNotFoundException {
    Product product = mock(Product.class);
    Inventory availableInventory1 = new Inventory();
    availableInventory1.setStatus(InventoryStatus.IN_CART);
    Inventory availableInventory2 = new Inventory();
    availableInventory2.setStatus(InventoryStatus.IN_CART);
    when(inventoryRepository.findInventoriesByProduct_IdAndStatus(product.getId(), InventoryStatus.AVAILABLE)).thenReturn(Arrays.asList(availableInventory1, availableInventory2));
    Inventory inventory = inventoryService.getInventoryToAdd(product.getId());
    assert inventory.getStatus().equals(InventoryStatus.IN_CART);
  }

}