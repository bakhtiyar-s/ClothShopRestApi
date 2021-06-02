package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends GenericService<Product, Integer, ProductRepository> {

    public List<Product> findProductsByCategoryId(int id) {
        return super.r.findProductsByCategoryId(id);
    }

    public void updateById(int id, Product product) {
        Product oldProduct = super.findById(id);
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());
        oldProduct.setCategoryId(product.getCategoryId());
        super.save(oldProduct);
    }

}
