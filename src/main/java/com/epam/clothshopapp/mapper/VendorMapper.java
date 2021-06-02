package com.epam.clothshopapp.mapper;

import com.epam.clothshopapp.mapper.dto.VendorDto;
import com.epam.clothshopapp.model.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorMapper extends DtoMapper<Vendor, VendorDto> {
}
