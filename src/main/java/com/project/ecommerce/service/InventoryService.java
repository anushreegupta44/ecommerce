package com.project.ecommerce.service;

import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class InventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  public ValidationResponse validateInventoryForProductExists(Integer productId) {
    List<Inventory> inventoryAvailableForSale = inventoryRepository.getInventoriesByProduct_IdAndSoldFalse(productId);
    if (isNull(inventoryAvailableForSale) || inventoryAvailableForSale.size() == 0) {
      return new ValidationResponse(false, new HashMap<String, String>() {{
        put("inventory", "inventory.empty.for.product");
      }});
    }
    return new ValidationResponse(true, null);
  }

  public Inventory markInventoryAsSold(Inventory inventoryToAdd) {
    inventoryToAdd.setSold(true);
    Inventory savedInventory = inventoryRepository.save(inventoryToAdd);
    return savedInventory;
  }
}
