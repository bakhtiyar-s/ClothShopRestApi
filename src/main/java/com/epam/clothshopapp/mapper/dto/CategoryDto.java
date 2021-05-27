package com.epam.clothshopapp.mapper.dto;

import com.epam.clothshopapp.model.Product;
import lombok.Data;
import java.util.List;

@Data
public class CategoryDto {

    private int id;
    private String name;
    private List<ProductDto> products;
}
