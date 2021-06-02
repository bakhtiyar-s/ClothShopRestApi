package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Api(tags = {SwaggerConfig.PRODUCT})
@PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DtoMapper<Product, ProductDto> productMapper;

    @GetMapping
    public ResponseEntity findAllProducts() {
        try {
            List<Product> products = productService.findAll();
            List<ProductDto> productDtos = products.stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity saveProduct(@RequestBody ProductDto productDto) {
        try {
            Product product = productService.save(productMapper.fromDto(productDto));
            return new ResponseEntity<>(productMapper.toDto(product) ,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findProductById(@PathVariable("id") int id) {
        try {
            Product product = productService.findById(id);
            return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateProductById(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        try {
            productService.updateById(id, productMapper.fromDto(productDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") int id) {
        try {
            productService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
