package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    void saveProduct(Product product);

    Optional<Product> findProductById(int id);

    List<Product> findProductsByCategoryId(int categoryId);

    void updateProductById(int id, Product product);

    void deleteProductById(int id);
}
