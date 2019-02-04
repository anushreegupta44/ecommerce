package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CategoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  public void shouldUpdateProductCategoriesToEqualProductDtosCategories() {
    Category category = mock(Category.class);
    List<Category> categoryList = new ArrayList<>();
    categoryList.add(category);
    //creating product with one category
    Product product = new Product("product1", "This is product1", categoryList);

    CategoryDto categoryDto = mock(CategoryDto.class);
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    categoryDtoList.add(categoryDto);
    Category category2 = mock(Category.class);
    //Product dto has another category that we want to update
    ProductDto productDto = new ProductDto("product1", "This is product1", categoryDtoList);

    //need to make sure that the category in product dto actually exists in the database
    when(categoryRepository.getCategoryByName(categoryDto.getName())).thenReturn(Optional.ofNullable(category2));

    //actual method call should return a list of old category plus the updated category from Dto
    List<Category> updatedCategories = productService.updateProductCategories(product, productDto);
    assert (updatedCategories.size() == 2);
  }

  @Test
  public void shouldNotUpdateProductCategoriesIfCategoryDoesNotExist() {
    Category category = mock(Category.class);
    List<Category> categoryList = new ArrayList<>();
    categoryList.add(category);
    //creating product with one category
    Product product = new Product("product1", "This is product1", categoryList);

    CategoryDto categoryDto = mock(CategoryDto.class);
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    categoryDtoList.add(categoryDto);
    Category category2 = mock(Category.class);
    //Product dto has another category that we want to update
    ProductDto productDto = new ProductDto("product1", "This is product1", categoryDtoList);

    //this time this category does not exist in the db
    when(categoryRepository.getCategoryByName(categoryDto.getName())).thenReturn(Optional.ofNullable(null));

    //actual method call should return a list of old category only
    List<Category> updatedCategories = productService.updateProductCategories(product, productDto);
    assert (updatedCategories.size() == 1);

  }

  @Test
  public void shouldUpdateProduct() {
    ProductDto productDto = mock(ProductDto.class);
    Product product = new Product("product", "description", null);
    when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
    productService.updateProduct(product.getId(), productDto);
    assertThat(product.getName(), is(productDto.getName()));
    verify(productRepository).save(product);
  }

  @Test
  public void shouldReturnValidationResponseWithErrorIfProductDoesNotExistInDb() {
    Product product = mock(Product.class);
    when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
    ValidationResponse response = productService.validateProductWithIdExists(product.getId());
    assertThat(response.getValid(), is(false));
  }

  @Test
  public void shouldReturnValidationResponseIfProductExistsInDb() {
    Product product = mock(Product.class);
    when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
    ValidationResponse response = productService.validateProductWithIdExists(product.getId());
    assertThat(response.getValid(), is(true));
  }
}