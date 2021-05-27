package com.epam.clothshopapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ApiResponse {
    @Id
    private int code;
    private String type;
    private String message;
}
