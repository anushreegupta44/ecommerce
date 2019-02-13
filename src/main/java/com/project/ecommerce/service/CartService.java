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

  //kept as Transactional block since the inventory status should not be IN_CART unless it is actually mapped to a cart
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

  //Removing a product from cart is essentially deleting a cartInventory
  public void removeProductFromCart(Integer cartId, Integer productId) throws InventoryNotFoundException {
    cartInventoryService.deleteCartInventory(cartId, productId);
  }

  //gets every product and its quantity in a specific cart
  public List<InCartProduct> getItemsInCart(Integer cartId) {
    return cartInventoryService.getProductDetailsInCart(cartId);
  }

  public Order checkoutCart(Integer cartId) throws CartEmptyException, CustomerNotFoundException, OrderNotFoundException {
    //pull out all inventories from cart
    List<CartInventory> inventoriesInCart = cartInventoryService.getAllInventoriesInCart(cartId);
    if (inventoriesInCart == null || inventoriesInCart.size() == 0) {
      throw new CartEmptyException();
    }
    //create an order and map inventory to order
    Order createdOrder = orderService.createOrder(inventoriesInCart);
    //delete all inventories from cart(cartInventory mapping)
    cartInventoryService.deleteCartInventoriesInCart(cartId);
    //mark inventories as SOLD
    inventoriesInCart.forEach(cartInventory -> {
      inventoryService.markInventoryWithStatus(cartInventory.getInventory(), InventoryStatus.SOLD);
    });
    return orderService.editOrderDetails(createdOrder);
  }

  public void deleteUserCart(Integer customerId) {
    //find the cart that belongs to user
    Optional<Cart> customerCart = cartRepository.findCartByCustomer_Id(customerId);
    if (customerCart.isPresent()) {
      //find all cartInventories for the customer and delete them
      cartInventoryService.deleteCartInventoriesInCart(customerCart.get().getId());
      //delete the cart finally
      cartRepository.delete(customerCart.get());
    }
  }
}
