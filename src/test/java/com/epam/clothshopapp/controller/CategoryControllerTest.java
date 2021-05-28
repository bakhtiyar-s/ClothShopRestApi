package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.CategoryDto;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.clothshopapp.controller.testUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DtoMapper dtoMapper;

    @Test
    void findAllCategories() throws Exception {
        populateCategories();
        List<Category> fromDb = categoryService.findAllCategories();

        String httpResponse = mockMvc.perform(get("/api/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CategoryDto> categoryDtos = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        List<Category> categories = categoryDtos.stream().map(categoryDto -> dtoMapper.categoryDtoToCategory(categoryDto)).collect(Collectors.toList());

        assertEquals(fromDb.toString(), categories.toString());

    }

    @Test
    void saveCategory() throws Exception {
        Category categoryToSave = createCategory();
        String requestBody = objectMapper.writeValueAsString(dtoMapper.categoryToCategoryDto(categoryToSave));

        String httpResponse = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryDto categoryDto = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        Category category = dtoMapper.categoryDtoToCategory(categoryDto);
        Optional<Category> fromDb = categoryService.findCategoryById(category.getId());

        assertTrue(fromDb.isPresent());
        assertEquals(fromDb.get().toString(), category.toString());
    }

    @Test
    void findCategoryById() throws Exception {
        Category categoryToSave = createCategory();
        Category savedCategory = testUtils.saveCategory(categoryToSave);

        String httpResponse = mockMvc.perform(get("/api/categories/" + savedCategory.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryDto categoryDto = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        Category category = dtoMapper.categoryDtoToCategory(categoryDto);

        assertEquals(savedCategory.toString(), category.toString());
    }

    @Test
    void findProductsByCategoryId() throws Exception {
        List<Product> savedProducts = populateProducts();

        String httpResponse = mockMvc.perform(get("/api/categories/" + savedProducts.get(0).getCategoryId() + "/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProductDto> productDtos = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        List<Product> products = productDtos.stream().map(productDto -> dtoMapper.productDtoToProduct(productDto)).collect(Collectors.toList());

        assertFalse(products.isEmpty());
        assertEquals(savedProducts.toString(), products.toString());

    }
}
