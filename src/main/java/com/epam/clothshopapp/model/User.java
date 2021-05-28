package com.epam.clothshopapp.model;

import com.epam.clothshopapp.security.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval=false)
    @JoinColumn(name = "user_id", nullable = true)
    private List<Order> orders;

}
