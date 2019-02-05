package com.project.ecommerce.service;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.CartItemKey;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.repository.CartItemRepository;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private InventoryRepository inventoryRepository;
  @Autowired
  private InventoryService inventoryService;

  public void addProductToCartForUser(Integer customerId, Integer productId) {
    CartItem cartItemToAdd = new CartItem();
    Optional<Customer> customer = customerRepository.findById(customerId);
    cartItemToAdd.setCustomer(customer.get());
    List<Inventory> inventoryList = inventoryRepository.getInventoriesByProduct_IdAndSoldFalse(productId);
    Inventory inventory = inventoryList.get(0);
    cartItemToAdd.setItem(inventory);
    CartItemKey cartItemKey = new CartItemKey(customerId, inventory.getSku());
    cartItemToAdd.setCartItemKey(cartItemKey);
    cartItemRepository.save(cartItemToAdd);
    inventoryService.markInventoryAsSold(inventory);
  }
}
