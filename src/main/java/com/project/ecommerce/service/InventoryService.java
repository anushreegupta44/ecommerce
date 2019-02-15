package com.project.ecommerce.service;

import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class InventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ProductService productService;

  public Inventory getInventoryToAdd(Integer productId) throws InventoryNotFoundException {
    List<Inventory> availableInventoryList = inventoryRepository.findInventoriesByProduct_IdAndStatus(productId, InventoryStatus.AVAILABLE);
    Optional<Inventory> availableInventory = availableInventoryList.stream().findAny();
    return availableInventory.orElseGet(() -> getInventoryInCart(productId));
  }

  public Inventory getInventoryInCart(Integer productId) throws InventoryNotFoundException {
    return inventoryRepository.findInventoriesByProduct_IdAndStatus(productId, InventoryStatus.IN_CART).stream().findAny().orElseThrow(InventoryNotFoundException::new);
  }

  public Inventory markInventoryWithStatus(Inventory inventory, InventoryStatus status) {
    inventory.setStatus(status);
    return inventoryRepository.save(inventory);
  }

  public Inventory addInventoryForProduct(String inventorySku, Integer productId) throws ProductNotFoundException {
    Product product = productService.getProductById(productId);
    Inventory inventory = new Inventory(inventorySku, product, InventoryStatus.AVAILABLE, null);
    return inventoryRepository.save(inventory);
  }
}
