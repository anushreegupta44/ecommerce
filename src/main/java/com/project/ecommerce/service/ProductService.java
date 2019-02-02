package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.CategoryRepository;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;

  @Autowired
  InventoryRepository inventoryRepository;

  @Autowired
  CategoryRepository categoryRepository;

  public ProductDto getProductById(Integer productId) {

    Optional<Product> product = productRepository.findById(productId);
    if (product.isPresent()) {
      //get the total number of inventory that is not sold for this product
      Integer invCountForProduct = inventoryRepository.getInventoriesByProduct_IdAndSoldFalse(productId).size();
      ProductDto productDto = convertProductToProductDto(product.get(), invCountForProduct);
      return productDto;
    } else
      return null;
  }

  public Integer createProduct(ProductDto productDto) {
    Product product = new Product();
    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    List<Category> categories = new ArrayList<>();
    for (CategoryDto categoryDto :
        productDto.getCategories()) {
      Optional<Category> category = categoryRepository.getCategoryByName(categoryDto.getName());
      if (category.isPresent()) {
        categories.add(category.get());
      }
    }
    product.setCategories(categories);
    Product savedProduct = productRepository.save(product);
    return savedProduct.getId();
  }

  private ProductDto convertProductToProductDto(Product product, Integer availableProductQuantity) {
    ProductDto productDto = new ProductDto();
    productDto.setName(product.getName());
    productDto.setDescription(product.getDescription());
    List<CategoryDto> categoryDtos = new ArrayList<>();
    for (Category category :
        product.getCategories()) {
      CategoryDto categoryDto = new CategoryDto(category.getName());
      categoryDtos.add(categoryDto);
    }
    productDto.setCategories(categoryDtos);
    productDto.setAvailableQuantity(availableProductQuantity);
    return productDto;
  }

  public ValidationResponse validateProductWithIdExists(Integer productId) {
    Optional<Product> product = productRepository.findById(productId);
    if (!product.isPresent()) {
      return new ValidationResponse(false, new HashMap<String, String>() {{
        put("product", "product.does.not.exist");
      }});
    } else return new ValidationResponse(true, null);
  }

  public void remove(Integer productId) {
    productRepository.deleteById(productId);
  }
}

