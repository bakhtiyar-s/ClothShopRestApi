package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.OrderDto;
import com.epam.clothshopapp.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends DtoMapper<Order, OrderDto> {
}
