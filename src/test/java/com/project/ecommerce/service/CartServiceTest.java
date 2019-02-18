package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderDetails;
import com.project.ecommerce.exception.*;
import com.project.ecommerce.model.*;
import com.project.ecommerce.repository.CartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceTest {
  @InjectMocks
  private CartService cartService;

  @Mock
  private InventoryService inventoryService;

  @Mock
  private CartInventoryService cartInventoryService;

  @Mock
  private OrderService orderService;

  @Mock
  private CartRepository cartRepository;

  @Test(expected = InventoryNotFoundException.class)
  public void shouldThrowExceptionIfInventoryDoesNotExistForProduct() throws InventoryNotFoundException, CartNotFoundException, InventoryAlreadyInCartException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenThrow(InventoryNotFoundException.class);
    cartService.addProductToCart(cart.getId(), product.getId());
  }

  @Test(expected = CartNotFoundException.class)
  public void shouldThrowExceptionIfCartDoesNotExistForProduct() throws InventoryNotFoundException, CartNotFoundException, InventoryAlreadyInCartException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenReturn(inventory);
    CartService spyCartService = spy(cartService);
    doThrow(new CartNotFoundException()).when(spyCartService).getCartById(Mockito.anyInt());
    spyCartService.addProductToCart(cart.getId(), product.getId());
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void shouldThrowExceptionIfCarAlreadyMappedToInventory() throws DataIntegrityViolationException, InventoryNotFoundException, CartNotFoundException, InventoryAlreadyInCartException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenReturn(inventory);
    CartService spyCartService = spy(cartService);
    doReturn(cart).when(spyCartService).getCartById(Mockito.anyInt());
    doThrow(DataIntegrityViolationException.class).when(cartInventoryService).mapCartToInventory(cart, inventory);
    spyCartService.addProductToCart(cart.getId(), product.getId());
  }

  @Test
  public void shouldMapInventoryAndMarkInventoryToInCart() throws DataIntegrityViolationException, InventoryNotFoundException, CartNotFoundException, InventoryAlreadyInCartException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenReturn(inventory);
    CartService spyCartService = spy(cartService);
    doReturn(cart).when(spyCartService).getCartById(Mockito.anyInt());
    spyCartService.addProductToCart(cart.getId(), product.getId());
    verify(cartInventoryService).mapCartToInventory(cart, inventory);
    verify(inventoryService).markInventoryWithStatus(inventory, InventoryStatus.IN_CART);
  }

  @Test(expected = InventoryNotFoundException.class)
  public void shouldThrowAnExceptionIfNoInventoryForProductExistsInCart() throws InventoryNotFoundException {
    doThrow(new InventoryNotFoundException()).when(cartInventoryService).deleteCartInventory(anyInt(), anyInt());
    cartService.removeProductFromCart(2, 2);
  }

  @Test(expected = CartEmptyException.class)
  public void shouldThrowErrIfCartEmptyOnCheckout() throws CustomerNotFoundException, CartEmptyException, OrderNotFoundException {
    when(cartInventoryService.getAllInventoriesInCart(anyInt())).thenReturn(Arrays.asList());
    cartService.checkoutCart(2);
  }

  @Test
  public void shouldMarkBothInventoriesAsSold() throws CustomerNotFoundException, CartEmptyException, OrderNotFoundException {
    Order order = mock(Order.class);
    OrderDetails orderDetails = mock(OrderDetails.class);
    CartInventory cartInventory1 = mock(CartInventory.class);
    CartInventory cartInventory2 = mock(CartInventory.class);

    when(cartInventoryService.getAllInventoriesInCart(anyInt())).thenReturn(Arrays.asList(cartInventory1, cartInventory2));
    when(orderService.createOrder(anyList())).thenReturn(order);
    when(orderService.getOrderDetails(anyInt())).thenReturn(orderDetails);

    cartService.checkoutCart(2);

    verify(inventoryService, times(2)).markInventoryWithStatus(cartInventory1.getInventory(), InventoryStatus.SOLD);
  }

  @Test(expected = CartNotFoundException.class)
  public void shouldThrowExceptionIfCartNotFound() {
    when(cartRepository.findCartByCustomer_Id(anyInt())).thenReturn(Optional.empty());
    cartService.getCartForCustomer(2);
  }

  @Test
  public void shouldReturnCartForCustomer() {
    Cart cart = mock(Cart.class);
    when(cartRepository.findCartByCustomer_Id(anyInt())).thenReturn(Optional.ofNullable(cart));
    Cart returnedCart = cartService.getCartForCustomer(2);
    assertNotNull(returnedCart);
  }
}