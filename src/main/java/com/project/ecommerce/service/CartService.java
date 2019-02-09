package com.project.ecommerce.service;

import com.project.ecommerce.exception.CartNotFoundException;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.repository.CartRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CartService {
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private CartInventoryService cartInventoryService;

  //kept as Transactional block since the inventory status should not be IN_CART unless it is actually mapped to a cart
  @Transactional
  public void addProductToCart(Integer cartId, Integer productId) throws InventoryNotFoundException, CartNotFoundException, DataIntegrityViolationException, ConstraintViolationException {
    Inventory availableInventory = inventoryService.getInventoryToAdd(productId);
    Cart cart = this.getCartById(cartId);
    cartInventoryService.mapCartToInventory(cart, availableInventory);
    inventoryService.markInventoryAsInCart(availableInventory);
  }

  public Cart getCartById(Integer cartId) throws CartNotFoundException {
    return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());
  }

  public Cart createCartForUser(Customer savedCustomer) {
    Cart cart = new Cart(savedCustomer, null);
    return cartRepository.save(cart);
  }
}
