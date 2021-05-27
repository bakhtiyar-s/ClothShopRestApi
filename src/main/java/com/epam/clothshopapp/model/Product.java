package com.epam.clothshopapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer price;
    private Integer quantity;
    @Column(name = "category_id")
    private Integer categoryId;

}
