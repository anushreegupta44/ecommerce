package com.project.ecommerce.service;

import com.project.ecommerce.dto.InCartProduct;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.CartInventory;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.InventoryStatus;
import com.project.ecommerce.repository.CartInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartInventoryService {
  @Autowired
  private CartInventoryRepository cartInventoryRepository;

  @Autowired
  private InventoryService inventoryService;

  public void mapCartToInventory(Cart cart, Inventory availableInventory) {
    CartInventory cartInventory = new CartInventory(cart, availableInventory);
    cartInventoryRepository.save(cartInventory);
  }

  @Transactional
  public void deleteCartInventory(Integer cartId, Integer productId) throws InventoryNotFoundException {
    List<CartInventory> cartInventories = getAllProductInventoriesInCart(cartId, productId);
    CartInventory cartInventory = cartInventories.stream().findAny().orElseThrow(() -> new InventoryNotFoundException());
    markStatusForCartInventory(cartInventory.getInventory());
    cartInventoryRepository.delete(cartInventory);
  }

  public void markStatusForCartInventory(Inventory inventory) {
    Boolean inventoryInOtherCart = isInventoryInOtherCart(inventory);
    if (!inventoryInOtherCart) {
      inventoryService.markInventoryWithStatus(inventory, InventoryStatus.AVAILABLE);
    }
  }

  public Boolean isInventoryInOtherCart(Inventory inventory) {
    List<CartInventory> carts = cartInventoryRepository.findCartInventoriesByInventory(inventory);
    if (carts == null || carts.size() == 1) {
      return false;
    }
    return true;
  }

  public List<CartInventory> getAllProductInventoriesInCart(Integer cartId, Integer productId) {
    return cartInventoryRepository.findCartInventoriesByCart_IdAndInventory_ProductId(cartId, productId);
  }

  public List<InCartProduct> getProductDetailsInCart(Integer cartId) {
    return cartInventoryRepository.findProductDetailsAndInventoryQuantityInCart(cartId);
  }

  public List<CartInventory> getAllInventoriesInCart(Integer cartId) {
    return cartInventoryRepository.findCartInventoriesByCart_Id(cartId);
  }

  public void deleteCartInventoriesInCart(Integer cartId) {
    cartInventoryRepository.deleteAllByCart_Id(cartId);
  }
}
