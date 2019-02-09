package com.project.ecommerce.service;

import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
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

  public ValidationResponse validateInventoryForProductExists(Integer productId) {
    List<Inventory> inventoryAvailableForSale = inventoryRepository.findInventoriesByProduct_IdAndStatus(productId, InventoryStatus.AVAILABLE);
    if (isNull(inventoryAvailableForSale) || inventoryAvailableForSale.size() == 0) {
      return new ValidationResponse(false, new HashMap<String, String>() {{
        put("inventory", "inventory.empty.for.product");
      }});
    }
    return new ValidationResponse(true, null);
  }

  //getting an inventory for the product that is AVAILABLE. If no inventory is AVAILABLE, getting inventory that is IN_CART but not in the same cart
  public Inventory getInventoryToAdd(Integer productId) throws InventoryNotFoundException {
    List<Inventory> availableInventoryList = inventoryRepository.findInventoriesByProduct_IdAndStatus(productId, InventoryStatus.AVAILABLE);
    Optional<Inventory> availableInventory = availableInventoryList.stream().findAny();
    if (availableInventory.isPresent()) {
      return availableInventory.get();
    } else
      return getInventoryInCart(productId);
  }

  private Inventory getInventoryInCart(Integer productId) throws InventoryNotFoundException {
    return inventoryRepository.findInventoriesByProduct_IdAndStatus(productId, InventoryStatus.IN_CART).stream().findAny().orElseThrow(() -> new InventoryNotFoundException());
  }

  public Inventory markInventoryWithStatus(Inventory inventory, InventoryStatus status) {
    inventory.setStatus(status);
    Inventory savedInventory = inventoryRepository.save(inventory);
    return savedInventory;
  }
}
