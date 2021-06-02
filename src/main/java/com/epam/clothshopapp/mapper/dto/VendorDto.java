package com.epam.clothshopapp.mapper.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VendorDto implements Serializable {
    private int id;
    private String name;
    private List<ProductDto> products;

}
