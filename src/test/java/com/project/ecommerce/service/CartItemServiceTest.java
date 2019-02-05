package com.project.ecommerce.service;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Customer;
import com.project.ecommerce.model.Inventory;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CartItemRepository;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.repository.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartItemServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private InventoryRepository inventoryRepository;

  @Mock
  private InventoryService inventoryService;

  @Mock
  private CartItemRepository cartItemRepository;

  @InjectMocks
  private CartItemService cartItemService;

  @Test
  public void shouldAddProductToCart() {
    Customer customer = mock(Customer.class);
    when(customerRepository.findById(customer.getId())).thenReturn(java.util.Optional.ofNullable(customer));

    Product product = mock(Product.class);
    Inventory inventory = mock(Inventory.class);
    List<Inventory> inventories = new ArrayList<>();
    inventories.add(inventory);
    when(inventoryRepository.getInventoriesByProduct_IdAndSoldFalse(product.getId())).thenReturn(inventories);

    CartItem cartItem = new CartItem(customer, inventories.get(0));

    cartItemService.addProductToCartForUser(customer.getId(), product.getId());
    verify(cartItemRepository).save(cartItem);
    assertThat(cartItem.getCustomer(), is(customer));
    assertThat(cartItem.getItem(), is(inventories.get(0)));
    verify(inventoryService).markInventoryAsSold(inventories.get(0));

  }
}