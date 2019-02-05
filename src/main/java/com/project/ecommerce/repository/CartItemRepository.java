package com.project.ecommerce.repository;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemKey> {
}
