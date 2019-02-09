package com.project.ecommerce.repository;

import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

  List<Inventory> findInventoriesByProduct_IdAndStatus(Integer productId, InventoryStatus status);
}
