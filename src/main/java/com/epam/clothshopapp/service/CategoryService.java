package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends GenericService<Category, Integer, CategoryRepository > {

}
