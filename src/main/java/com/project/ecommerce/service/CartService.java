package com.project.ecommerce.service;

import com.project.ecommerce.dto.InCartProduct;
import com.project.ecommerce.exception.*;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private CartInventoryService cartInventoryService;

  @Autowired
  private OrderService orderService;

  @Transactional
  public void addProductToCart(Integer cartId, Integer productId) throws InventoryNotFoundException, CartNotFoundException, InventoryAlreadyInCartException {
    Inventory availableInventory = inventoryService.getInventoryToAdd(productId);
    Cart cart = this.getCartById(cartId);
    cartInventoryService.mapCartToInventory(cart, availableInventory);
    inventoryService.markInventoryWithStatus(availableInventory, InventoryStatus.IN_CART);
  }

  public Cart getCartById(Integer cartId) throws CartNotFoundException {
    return cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());
  }

  public Cart createCartForUser(Customer savedCustomer) {
    Cart cart = new Cart(savedCustomer, null);
    return cartRepository.save(cart);
  }

  public void removeProductFromCart(Integer cartId, Integer productId) throws InventoryNotFoundException {
    cartInventoryService.deleteCartInventory(cartId, productId);
  }

  public List<InCartProduct> getItemsInCart(Integer cartId) {
    return cartInventoryService.getProductDetailsInCart(cartId);
  }

  public Order checkoutCart(Integer cartId) throws CartEmptyException, CustomerNotFoundException, OrderNotFoundException {
    List<CartInventory> inventoriesInCart = cartInventoryService.getAllInventoriesInCart(cartId);
    if (inventoriesInCart == null || inventoriesInCart.size() == 0) {
      throw new CartEmptyException(cartId.toString());
    }
    Order createdOrder = orderService.createOrder(inventoriesInCart);
    cartInventoryService.deleteCartInventoriesInCart(cartId);
    inventoriesInCart.forEach(cartInventory -> {
      inventoryService.markInventoryWithStatus(cartInventory.getInventory(), InventoryStatus.SOLD);
    });
    return createdOrder;
  }

  public void deleteUserCart(Integer customerId) {
    Optional<Cart> customerCart = cartRepository.findCartByCustomer_Id(customerId);
    if (customerCart.isPresent()) {
      cartInventoryService.deleteCartInventoriesInCart(customerCart.get().getId());
      cartRepository.delete(customerCart.get());
    }
  }

  public Cart getCartForCustomer(Integer customerId) throws CartNotFoundException {
    return cartRepository.findCartByCustomer_Id(customerId).orElseThrow(CartNotFoundException::new);
  }
}
