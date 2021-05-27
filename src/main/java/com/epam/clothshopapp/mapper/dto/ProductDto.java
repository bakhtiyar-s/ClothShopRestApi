package com.epam.clothshopapp.mapper.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer categoryId;

}
