package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.CategoryService;
import com.epam.clothshopapp.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Component
public class testUtils {
    @Autowired
    private CategoryService _categoryService;

    private static CategoryService categoryService;

    @PostConstruct
    public void init() {
        categoryService = _categoryService;

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
}
