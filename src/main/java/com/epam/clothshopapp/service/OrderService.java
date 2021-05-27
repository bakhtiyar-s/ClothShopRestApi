package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAllOrders();

    Optional<Order> findOrderById(int id);

    void deleteOrderById(int id);

    List<Product> findProductsByOrderId(int id);

    void saveProductToOrder(int id, Product product);

    Optional<Product> findProductByOrderIdItemId(int orderId, int productId);

    void deleteProductFromOrder(int orderId, int productId);

    void cancelOrder(int id);
}
