package com.epam.clothshopapp.mapper;

import org.springframework.stereotype.Service;

@Service
public interface DtoMapper<T, S> {
    T fromDto(S var);
    S toDto(T var);
}
