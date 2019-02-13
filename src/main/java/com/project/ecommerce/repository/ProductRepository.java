package com.project.ecommerce.repository;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  List<Product> findAllByCategories(Category category);
}
