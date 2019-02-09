package com.project.ecommerce.repository;

import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.CartInventoryId;
import com.project.ecommerce.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartInventoryRepository extends JpaRepository<CartInventory, CartInventoryId> {

  List<CartInventory> findCartInventoriesByCart_IdAndInventory_ProductId(Integer cartId, Integer productId);

  List<CartInventory> findCartInventoriesByInventory(Inventory inventory);
}
