package com.epam.clothshopapp.mapper.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserGetDto implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<OrderDto> orders;

}
