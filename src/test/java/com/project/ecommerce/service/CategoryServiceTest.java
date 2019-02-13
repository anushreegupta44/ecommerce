package com.project.ecommerce.service;

import com.project.ecommerce.exception.CategoryNotFoundException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryServiceTest {
  @InjectMocks
  private CategoryService categoryService;

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private ProductRepository productRepository;

  @Test
  public void getCategoryByNameIfCategoryExists() throws CategoryNotFoundException {
    Category category = mock(Category.class);
    when(categoryRepository.getCategoryByName(anyString())).thenReturn(java.util.Optional.ofNullable(category));
    Category recievedCategory = categoryService.getCategoryByName("name");
    assertThat(recievedCategory, instanceOf(Category.class));
  }

  @Test(expected = CategoryNotFoundException.class)
  public void throwCategoryNotFoundCategoryExists() throws CategoryNotFoundException {
    when(categoryRepository.getCategoryByName(anyString())).thenReturn(Optional.empty());
    categoryService.getCategoryByName("name");
  }

  @Test(expected = CategoryNotFoundException.class)
  public void throwCategoryNotFoundOnUpdateIfCategoryDoesNotExist() throws CategoryNotFoundException {
    Category category = mock(Category.class);
    CategoryService spyCategoryService = Mockito.spy(categoryService);
    when(spyCategoryService.getCategoryById(anyInt())).thenThrow(new CategoryNotFoundException());
    spyCategoryService.updateCategory(2, category);
  }

  @Test
  public void shouldUpdateCategory() throws CategoryNotFoundException {
    Category category = mock(Category.class);
    CategoryService spyCategoryService = Mockito.spy(categoryService);
    Mockito.doReturn(category).when(spyCategoryService).getCategoryById(anyInt());
    spyCategoryService.updateCategory(category.getId(), category);
    verify(categoryRepository).save(category);
  }

  @Test
  public void shouldRemoveCategory() throws CategoryNotFoundException {
    Category category = mock(Category.class);
    CategoryService spyCategoryService = Mockito.spy(categoryService);
    Mockito.doReturn(category).when(spyCategoryService).getCategoryById(anyInt());
    spyCategoryService.remove(category.getId());
    verify(categoryRepository).delete(category);
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  public void shouldSaveCategory() throws CategoryNotFoundException {
    Category category = mock(Category.class);
    Product product = mock(Product.class);
    CategoryService spyCategoryService = Mockito.spy(categoryService);
    when(productRepository.findAllByCategories(category)).thenReturn(Arrays.asList(product));
    Mockito.doReturn(category).when(spyCategoryService).getCategoryById(anyInt());
    spyCategoryService.remove(category.getId());
    verify(categoryRepository).delete(category);
    verify(productRepository).save(any(Product.class));
  }
}