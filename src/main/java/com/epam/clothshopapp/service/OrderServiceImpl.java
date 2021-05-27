package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Status;
import com.epam.clothshopapp.repository.OrderRepository;
import com.epam.clothshopapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrderById(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductsByOrderId(int id) {
        return orderRepository.findProductsByOrderId(id);
    }

    @Override
    public void saveProductToOrder(int id, Product product) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            List<Product> products = order.get().getItems();
            productRepository.save(product);
            products.add(product);
            order.get().setItems(products);
            orderRepository.save(order.get());
        }
    }

    @Override
    public Optional<Product> findProductByOrderIdItemId(int orderId, int productId) {
        return findProductsByOrderId(orderId).stream().filter(product -> product.getId() == productId).findFirst();
    }

    @Override
    public void deleteProductFromOrder(int orderId, int productId) {
        Optional<Product> product = findProductByOrderIdItemId(orderId, productId);
        product.ifPresent(value -> productRepository.delete(value));
    }

    @Override
    public void cancelOrder(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setStatus(Status.CANCELLED);
            orderRepository.save(order.get());
        }
    }
}
