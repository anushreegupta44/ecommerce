package com.project.ecommerce.service;

import com.project.ecommerce.exception.CartNotFoundException;
import com.project.ecommerce.exception.InventoryNotFoundException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceTest {
  @InjectMocks
  private CartService cartService;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private InventoryService inventoryService;

  @Mock
  private CartInventoryService cartInventoryService;


  @Test(expected = InventoryNotFoundException.class)
  public void shouldThrowExceptionIfInventoryDoesNotExistForProduct() throws InventoryNotFoundException, CartNotFoundException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenThrow(InventoryNotFoundException.class);
    cartService.addProductToCart(cart.getId(), product.getId());
  }

  @Test(expected = CartNotFoundException.class)
  public void shouldThrowExceptionIfCartDoesNotExistForProduct() throws InventoryNotFoundException, CartNotFoundException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenReturn(inventory);
    CartService spyCartService = spy(cartService);
    doThrow(new CartNotFoundException()).when(spyCartService).getCartById(Mockito.anyInt());
    spyCartService.addProductToCart(cart.getId(), product.getId());
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void shouldThrowExceptionIfCarAlreadyMappedToInventory() throws DataIntegrityViolationException, InventoryNotFoundException, CartNotFoundException {
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
  public void shouldMapInventoryAndMarkInventoryToInCart() throws DataIntegrityViolationException, InventoryNotFoundException, CartNotFoundException {
    Product product = mock(Product.class);
    Cart cart = mock(Cart.class);
    Inventory inventory = mock(Inventory.class);
    when(inventoryService.getInventoryToAdd(product.getId())).thenReturn(inventory);
    CartService spyCartService = spy(cartService);
    doReturn(cart).when(spyCartService).getCartById(Mockito.anyInt());
    spyCartService.addProductToCart(cart.getId(), product.getId());
    verify(cartInventoryService).mapCartToInventory(cart, inventory);
    verify(inventoryService).markInventoryAsInCart(inventory);
  }


}