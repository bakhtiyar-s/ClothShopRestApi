package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.*;
import com.epam.clothshopapp.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToCategoryDto(Category category);

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    VendorDto vendorToVendorDto(Vendor vendor);

    Vendor vendorDtoToVendor(VendorDto vendorDto);

    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);

    UserGetDto userToUserGetDto(User user);

    User userPostDtoToUser(UserPostDto userPostDto);

}
