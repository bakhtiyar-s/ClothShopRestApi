package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.CategoryDto;
import com.epam.clothshopapp.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends DtoMapper<Category, CategoryDto> {
}
