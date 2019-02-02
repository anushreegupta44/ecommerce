package com.project.ecommerce.service;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.InventoryRepository;
import com.project.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
  @Autowired
  ProductRepository productRepository;

  @Autowired
  InventoryRepository inventoryRepository;

  public ProductDto getProductById(Integer productId) {

    Optional<Product> product = productRepository.findById(productId);
    if (product.isPresent()) {
      //get the total number of inventory that is not sold for this product
      Integer invCountForProduct = inventoryRepository.getInventoriesByProduct_Id(productId).size();
      ProductDto productDto = convertProductToProductDto(product.get(), invCountForProduct);
      return productDto;
    } else
      return null;


  }

  private ProductDto convertProductToProductDto(Product product, Integer availableProductQuantity) {
    ProductDto productDto = new ProductDto();
    productDto.setName(product.getName());
    productDto.setDescription(product.getDescription());
    productDto.setCategories(product.getCategories());
    productDto.setAvailableQuantity(availableProductQuantity);
    return productDto;
  }
}

