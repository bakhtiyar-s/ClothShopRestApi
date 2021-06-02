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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@Api(tags = {SwaggerConfig.CATEGORY})
@PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final DtoMapper<Category, CategoryDto> categoryMapper;
    private final DtoMapper<Product, ProductDto> productMapper;

    @GetMapping
    @ApiOperation(value = "Product Categories")
    public ResponseEntity findAllCategories() {
        try {
            List<Category> categories = categoryService.findAll();
            List<CategoryDto> categoryDtos = categories.stream().map(category -> categoryMapper.toDto(category)).collect(Collectors.toList());
            return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a category")
    public ResponseEntity saveCategory(@RequestBody CategoryDto categoryDto) {
        try {
            Category category = categoryMapper.fromDto(categoryDto);
            CategoryDto result = categoryMapper.toDto(categoryService.save(category));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Individual Category")
    public ResponseEntity findCategoryById(@PathVariable("id") int id) {
        try {
            Category category = categoryService.findById(id);
            return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/products")
    @ApiOperation(value = "Get the products of a category")
    public ResponseEntity findProductsByCategoryId(@PathVariable("id") int categoryId) {
        try {
            List<Product> products = productService.findProductsByCategoryId(categoryId);
            List<ProductDto> productDtos = products.stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
