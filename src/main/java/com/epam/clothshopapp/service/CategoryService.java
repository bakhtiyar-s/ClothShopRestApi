package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAllCategories();

    Category saveCategory(Category category);

    Optional<Category> findCategoryById(int id);
}
