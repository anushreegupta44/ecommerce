package com.project.ecommerce.repository;

import com.project.ecommerce.model.OrderInventory;
import com.project.ecommerce.model.OrderInventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInventoryRepository extends JpaRepository<OrderInventory, OrderInventoryId> {
}
