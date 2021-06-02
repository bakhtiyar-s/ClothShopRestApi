package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.UserGetDto;
import com.epam.clothshopapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserGetMapper extends DtoMapper<User, UserGetDto> {
}
