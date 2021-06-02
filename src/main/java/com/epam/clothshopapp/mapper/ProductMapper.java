package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends DtoMapper<Product, ProductDto> {
}
