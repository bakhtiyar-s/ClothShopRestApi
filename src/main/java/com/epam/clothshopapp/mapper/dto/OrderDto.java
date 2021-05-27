package com.epam.clothshopapp.mapper.dto;

import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Status;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private int id;
    private String shipDate;
    private String createdAt;
    private Status status;
    private Boolean complete;
    private List<ProductDto> items;
}
