package com.epam.clothshopapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ship_date")
    private String shipDate;
    @Column(name = "created_at")
    private String createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Boolean complete;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = false)
    @JoinColumn(name = "order_id", nullable = true)
    private List<Product> items;

}
