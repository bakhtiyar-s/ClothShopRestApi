package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    void saveUser(User user);

    Optional<User> findUserById(int id);

    void updateUserById(int id, User user);

    void deleteUserById(int id);

    List<Order> findOrdersByUserId(int id);

    void saveOrderToUser(int id, Order order);

    User findUserByUsername(String username);
}
