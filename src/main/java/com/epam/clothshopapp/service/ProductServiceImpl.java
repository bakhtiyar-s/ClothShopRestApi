package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Optional<Product> findProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findProductsByCategoryId(int categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }

    @Override
    public void updateProductById(int id, Product product) {
        Optional<Product> oldProduct = productRepository.findById(id);
        if (oldProduct.isPresent()) {
            oldProduct.get().setName(product.getName());
            oldProduct.get().setPrice(product.getPrice());
            oldProduct.get().setQuantity(product.getQuantity());
            oldProduct.get().setCategoryId(product.getCategoryId());
            productRepository.save(oldProduct.get());
        }
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
