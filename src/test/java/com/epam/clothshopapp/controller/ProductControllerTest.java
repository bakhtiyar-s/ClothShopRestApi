package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.CategoryDto;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Category;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.ProductService;
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

import static com.epam.clothshopapp.controller.testUtils.createProduct;
import static com.epam.clothshopapp.controller.testUtils.populateProducts;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private DtoMapper<Product, ProductDto> productMapper;


    @Test
    void findAllProducts() throws Exception {
        List<Product> savedProducts = populateProducts();

        String httpResponse = mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProductDto> productDtos = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        List<Product> products = productDtos.stream().map(productDto -> productMapper.fromDto(productDto)).collect(Collectors.toList());

        assertFalse(products.isEmpty());
        assertEquals(savedProducts.toString(), products.toString());
    }

    @Test
    void saveProduct() throws Exception {
        Product productToSave = createProduct();
        String requestBody = objectMapper.writeValueAsString(productMapper.toDto(productToSave));

        String httpResponse = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto productDto = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        Product product = productMapper.fromDto(productDto);
        Product fromDb = productService.findById(product.getId());

        assertEquals(fromDb.toString(), product.toString());
    }

    @Test
    void findProductById() throws Exception {
        Product productToSave = createProduct();
        Product savedProduct = testUtils.saveProduct(productToSave);

        String httpResponse = mockMvc.perform(get("/api/products/" + savedProduct.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto productDto = objectMapper.readValue(httpResponse, new TypeReference<>() {});
        Product product = productMapper.fromDto(productDto);

        assertEquals(savedProduct.toString(), product.toString());
    }

    @Test
    void updateProductById() throws Exception {
        Product productToSave = createProduct();
        Product savedProduct = testUtils.saveProduct(productToSave);
        productToSave.setName("name_updated");
        String requestBody = objectMapper.writeValueAsString(productMapper.toDto(productToSave));

        mockMvc.perform(put("/api/products/" + savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        Product fromDb = productService.findById(savedProduct.getId());

        assertEquals(productToSave.getName(), fromDb.getName());
    }

    @Test
    void deleteProductById() throws Exception {
        Product productToSave = createProduct();
        Product savedProduct = testUtils.saveProduct(productToSave);

        mockMvc.perform(delete("/api/products/" + savedProduct.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        Product fromDb = productService.findById(savedProduct.getId());
        assertNull(fromDb);
    }
}
