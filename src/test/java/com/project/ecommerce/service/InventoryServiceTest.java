package com.project.ecommerce.service;

import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
    when(inventoryRepository.getInventoriesByProduct_IdAndSoldFalse(productIdForInventories)).thenReturn(inventories);
    ValidationResponse res = inventoryService.validateInventoryForProductExists(productIdForInventories);
    assertTrue(res.getValid());
  }

  @Test
  public void giveInvalidResIfInventoryForProductDoesNotExist() {
    Integer productIdForInventories = 1;
    List<Inventory> inventories = new ArrayList<>();
    when(inventoryRepository.getInventoriesByProduct_IdAndSoldFalse(productIdForInventories)).thenReturn(inventories);
    ValidationResponse res = inventoryService.validateInventoryForProductExists(productIdForInventories);
    assertFalse(res.getValid());
  }

  @Test
  public void shouldMarkInventoryAsSoldAndSaveIt() {
    Inventory inventory = mock(Inventory.class);
//    Inventory savedInventory = new Inventory();
    inventoryService.markInventoryAsSold(inventory);
//    when(inventoryRepository.save(inventory)).thenReturn(savedInventory);
    verify(inventoryRepository).save(inventory);
//    assertTrue(retInventory.getSold());
  }

}