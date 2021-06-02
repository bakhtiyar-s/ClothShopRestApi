package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.UserPostDto;
import com.epam.clothshopapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPostMapper extends DtoMapper<User, UserPostDto> {
}
