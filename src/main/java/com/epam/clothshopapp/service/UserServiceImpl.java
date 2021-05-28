package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.User;
import com.epam.clothshopapp.repository.OrderRepository;
import com.epam.clothshopapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUserById(int id, User user) {
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()) {
            oldUser.get().setUsername(user.getUsername());
            oldUser.get().setFirstName(user.getFirstName());
            oldUser.get().setLastName(user.getLastName());
            oldUser.get().setEmail(user.getEmail());
            oldUser.get().setPassword(user.getPassword());
            oldUser.get().setPhone(user.getPhone());
            oldUser.get().setOrders(user.getOrders());
            userRepository.save(oldUser.get());
        }
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Order> findOrdersByUserId(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getOrders).orElse(null);
    }

    @Override
    public void saveOrderToUser(int id, Order order) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Order> orders = user.get().getOrders();
            orderRepository.save(order);
            orders.add(order);
            user.get().setOrders(orders);
            userRepository.save(user.get());
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
