package com.project.ecommerce.repository;

import com.project.ecommerce.dto.OrderDetail;
import com.project.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

  @Query("SELECT new com.project.ecommerce.dto.OrderDetail(i.product.id, max(i.product.name)," +
      " max(i.product.description),max(i.product.pricePerUnit), count(oi) )" +
      "FROM OrderInventory oi " +
      "JOIN oi.inventory i " +
      "JOIN oi.inventory.product p " +
      "WHERE  oi.order.id=:orderId " +
      "GROUP BY i.product.id")
  List<OrderDetail> findOrderDetails(@Param("orderId") Integer orderId);
}
