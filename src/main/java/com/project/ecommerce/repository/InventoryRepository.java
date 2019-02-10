package com.project.ecommerce.repository;

import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

  List<Inventory> findInventoriesByProduct_IdAndStatus(Integer productId, InventoryStatus status);

  @Query("SELECT i FROM Inventory i LEFT JOIN i.carts c WHERE i.sku = c.inventory AND i.product.id=:productId AND c.cart.id=:cartId")
  List<Inventory> findInventoriesInCart_IdOfProduct_Id(@Param("cartId") Integer cartId, @Param("productId") Integer productId);
}