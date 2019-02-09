package com.project.ecommerce.service;

import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.repository.CartInventoryRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CartInventoryService {
  @Autowired
  private CartInventoryRepository cartInventoryRepository;

  //mapping cart to inventory since an inventory can be in many carts till its status is not SOLD, hence kept the mapping for cart and inventory as manytomany
  public void mapCartToInventory(Cart cart, Inventory availableInventory) throws DataIntegrityViolationException, ConstraintViolationException {
    CartInventory cartInventory = new CartInventory(cart, availableInventory);
    cartInventoryRepository.save(cartInventory);
  }
}
