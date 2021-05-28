package com.epam.clothshopapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval=false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    private List<Product> products;

}
