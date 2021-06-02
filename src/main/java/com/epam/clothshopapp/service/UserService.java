package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.User;
import com.epam.clothshopapp.repository.OrderRepository;
import com.epam.clothshopapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService extends GenericService<User, Integer, UserRepository> {

    OrderRepository orderRepository;

    public void updateById(int id, User user) {
        User oldUser = super.findById(id);
        oldUser.setUsername(user.getUsername());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setPhone(user.getPhone());
        oldUser.setOrders(user.getOrders());
        super.save(oldUser);
    }

    public List<Order> findOrdersByUserId(int id) {
        User user = super.findById(id);
        return user.getOrders();
    }

    public void saveOrderToUser(int id, Order order) {
        User user = super.findById(id);
        List<Order> orders = user.getOrders();
        orderRepository.save(order);
        orders.add(order);
        user.setOrders(orders);
        super.save(user);
    }

    public User findUserByUsername(String username) {
        return super.r.findUserByUsername(username);
    }
}
