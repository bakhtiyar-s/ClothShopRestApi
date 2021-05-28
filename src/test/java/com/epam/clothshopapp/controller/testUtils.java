package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.CategoryService;
import com.epam.clothshopapp.service.ProductService;
import com.epam.clothshopapp.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class testUtils {
    @Autowired
    private CategoryService _categoryService;
    @Autowired
    private ProductService _productService;

    private static CategoryService categoryService;
    private static ProductService productService;


    @PostConstruct
    public void init() {
        categoryService = _categoryService;
        productService = _productService;
    }

    public static void populateCategories() {
        for (int i = 1; i < 6; i++) {
            Category category = new Category();
            category.setId(i);
            category.setName("category_" + i);
            category.setProducts(new ArrayList<>());

            categoryService.saveCategory(category);
        }
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setName("my_category");
        category.setProducts(new ArrayList<>());
        return category;
    }

    public static Category saveCategory(Category category) {
        return categoryService.saveCategory(category);
    }

    public static List<Product> populateProducts() {
        Category categoryToSave = createCategory();
        int categoryId = testUtils.saveCategory(categoryToSave).getId();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Product product = new Product();
            product.setName("product_" + i);
            product.setPrice(i*100);
            product.setQuantity(i*10);
            product.setCategoryId(categoryId);

            Product savedProduct = productService.saveProduct(product);
            products.add(savedProduct);
        }
        return products;
    }

    public static Product createProduct() {
        Category categoryToSave = createCategory();
        int categoryId = testUtils.saveCategory(categoryToSave).getId();
        Product product = new Product();
        product.setName("my_product");
        product.setPrice(categoryId*100);
        product.setQuantity(categoryId*10);
        product.setCategoryId(categoryId);
        return product;
    }

    public static Product saveProduct(Product product) {
        return productService.saveProduct(product);
    }
}
