package com.epam.clothshopapp.mapper.dto;

import com.epam.clothshopapp.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserPostDto {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private List<OrderDto> orders;

}
