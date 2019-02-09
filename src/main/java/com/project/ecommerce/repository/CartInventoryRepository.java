package com.project.ecommerce.repository;

import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.CartInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartInventoryRepository extends JpaRepository<CartInventory, CartInventoryId> {
}
