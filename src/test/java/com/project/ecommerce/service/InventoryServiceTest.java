package com.project.ecommerce.service;

import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryServiceTest {

  @InjectMocks
  private InventoryService inventoryService;

  @Mock
  private InventoryRepository inventoryRepository;

  @Mock
  private ProductService productService;

  @Test
  public void givesAvailableInventoryToAddForProduct() throws InventoryNotFoundException {
    Product product = mock(Product.class);
    Inventory availableInventory1 = mock(Inventory.class);
    availableInventory1.setStatus(InventoryStatus.AVAILABLE);
    Inventory availableInventory2 = mock(Inventory.class);
    availableInventory2.setStatus(InventoryStatus.IN_CART);
    product.setInventories(Arrays.asList(availableInventory1, availableInventory2));
    when(inventoryRepository.findInventoriesByProduct_IdAndStatus(product.getId(), InventoryStatus.AVAILABLE)).thenReturn(Collections.singletonList(availableInventory1));
    Inventory inventory = inventoryService.getInventoryToAdd(product.getId());
    assertThat(inventory, is(availableInventory1));
  }

  @Test
  public void shouldMArkInventoryStatus() {
    Inventory inventory = mock(Inventory.class);
    inventory.setStatus(InventoryStatus.AVAILABLE);
    inventoryService.markInventoryWithStatus(inventory, InventoryStatus.IN_CART);
    verify(inventoryRepository).save(inventory);
    verify(inventory).setStatus(InventoryStatus.IN_CART);
  }

  @Test(expected = ProductNotFoundException.class)
  public void throwExceptionIfProductNotFound() throws ProductNotFoundException {
    when(productService.getProductById(anyInt())).thenThrow(new ProductNotFoundException());
    inventoryService.addInventoryForProduct("ID-21346", 2);
  }

  @Test
  public void shouldSaveInventory() throws ProductNotFoundException {
    Product product = mock(Product.class);
    when(productService.getProductById(anyInt())).thenReturn(product);
    inventoryService.addInventoryForProduct("ID-21346", 2);
    verify(inventoryRepository).save(any(Inventory.class));
  }
}