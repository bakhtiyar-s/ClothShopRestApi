package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.CategoryDto;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.CategoryService;
import com.epam.clothshopapp.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@Api(tags = {SwaggerConfig.CATEGORY})
public class CategoryController {
    private CategoryService categoryService;
    private ProductService productService;
    private DtoMapper dtoMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService, DtoMapper dtoMapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    @ApiOperation(value = "Product Categories")
    public ResponseEntity findAllCategories() {
        try {
            List<Category> categories = categoryService.findAllCategories();
            List<CategoryDto> categoryDtos = categories.stream().map(category -> dtoMapper.categoryToCategoryDto(category)).collect(Collectors.toList());
            return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a category")
    public ResponseEntity saveCategory(@RequestBody CategoryDto categoryDto) {
        try {
            Category category = dtoMapper.categoryDtoToCategory(categoryDto);
            CategoryDto result = dtoMapper.categoryToCategoryDto(categoryService.saveCategory(category));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Individual Category")
    public ResponseEntity findCategoryById(@PathVariable("id") int id) {
        try {
            Optional<Category> category = categoryService.findCategoryById(id);
            if (category.isPresent()) {
                return new ResponseEntity<>(dtoMapper.categoryToCategoryDto(category.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/products")
    @ApiOperation(value = "Get the products of a category")
    public ResponseEntity findProductsByCategoryId(@PathVariable("id") int categoryId) {
        try {
            List<Product> products = productService.findProductsByCategoryId(categoryId);
            List<ProductDto> productDtos = products.stream().map(product -> dtoMapper.productToProductDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
