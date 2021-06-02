package com.epam.clothshopapp.mapper.dto;

import com.epam.clothshopapp.model.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPostDto implements Serializable {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private List<OrderDto> orders;

}
