package com.project.ecommerce.repository;

import com.project.ecommerce.dto.InCartProduct;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.CartInventoryId;
import com.project.ecommerce.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartInventoryRepository extends JpaRepository<CartInventory, CartInventoryId> {

  List<CartInventory> findCartInventoriesByCart_IdAndInventory_ProductId(Integer cartId, Integer productId);

  List<CartInventory> findCartInventoriesByInventory(Inventory inventory);

  @Query("SELECT new com.project.ecommerce.dto.InCartProduct(i.product.id,  count(ci), max(i.product.name), max(i.product.description))" +
      "FROM CartInventory ci " +
      "JOIN ci.inventory i " +
      "JOIN ci.inventory.product p " +
      "WHERE  ci.cart.id=:cartId " +
      "GROUP BY i.product.id")
  List<InCartProduct> findProductDetailsAndInventoryQuantityInCart(@Param("cartId") Integer cartId);

  List<CartInventory> findCartInventoriesByCart_Id(Integer cartId);

  @Transactional
  void deleteAllByCart_Id(Integer cartId);
}
