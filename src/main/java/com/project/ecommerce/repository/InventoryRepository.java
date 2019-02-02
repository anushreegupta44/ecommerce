package com.project.ecommerce.repository;

import com.project.ecommerce.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {


//  @Query("SELECT count(sku)  " +
//      "FROM Inventory where product_id=:productId")
//  long getTotalQtyNotSold(@Param("productId") Integer productId);

  List<Inventory> getInventoriesByProduct_IdAndSoldFalse(Integer productId);


}
