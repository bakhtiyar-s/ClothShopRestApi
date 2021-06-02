package com.epam.clothshopapp.mapper.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private int id;
    private String name;
    private Integer price;
    private Integer quantity;
    private Integer categoryId;

}
