package com.project.ecommerce.service;

import com.project.ecommerce.exception.CategoryNotFoundException;
import com.project.ecommerce.exception.ProductNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Mock
  private CategoryService categoryService;

  @Test
  public void shouldUpdateProduct() throws ProductNotFoundException {
    Product product = mock(Product.class);
    when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
    productService.updateProduct(product.getId(), product);
    verify(productRepository).save(product);
  }

  @Test(expected = ProductNotFoundException.class)
  public void shouldNotUpdateProductIfProductDoesNotExist() throws ProductNotFoundException {
    Product product = mock(Product.class);
    ProductService spyProductService = Mockito.spy(productService);
    doThrow(new ProductNotFoundException()).when(spyProductService).getProductById(Mockito.anyInt());
    productService.updateProduct(product.getId(), product);
  }

  @Test
  public void shouldRemoveProductIfProductIdExists() throws ProductNotFoundException {
    Product product = mock(Product.class);
    ProductService spyProductService = Mockito.spy(productService);
    Mockito.doReturn(product).when(spyProductService).getProductById(Mockito.any());
    spyProductService.remove(product.getId());
    verify(productRepository).deleteById(product.getId());
  }

  @Test(expected = ProductNotFoundException.class)
  public void shouldNotRemoveProductIfProductIdExists() throws ProductNotFoundException {
    Product product = mock(Product.class);
    ProductService spyProductService = Mockito.spy(productService);
    doThrow(new ProductNotFoundException()).when(spyProductService).getProductById(Mockito.anyInt());
    spyProductService.remove(product.getId());
  }

  @Test
  public void shouldReturnValidCategories() throws CategoryNotFoundException {
    Category category1 = new Category("category1");
    Category category2 = new Category("category2");
    Product product = new Product("name", "description", Arrays.asList(category1, category2),null);
    when(categoryService.getCategoryByName(anyString())).thenReturn(category1).thenThrow(new CategoryNotFoundException());
    List<Category> validCategories = productService.getValidCategories(product);
    assertThat(validCategories.size(), is(1));
  }
}