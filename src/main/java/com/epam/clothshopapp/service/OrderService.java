package com.epam.clothshopapp.service;

import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.model.Status;
import com.epam.clothshopapp.repository.OrderRepository;
import com.epam.clothshopapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService extends GenericService<Order, Integer, OrderRepository> {

    ProductRepository productRepository;

    public List<Product> findProductsByOrderId(int id) {
        return super.r.findProductsByOrderId(id);
    }

    public void saveProductToOrder(int id, Product product) {
        Order order = super.findById(id);
        List<Product> products = order.getItems();
        productRepository.save(product);
        products.add(product);
        order.setItems(products);
        super.save(order);
    }

    public Product findProductByOrderIdItemId(int orderId, int productId) {
        return findProductsByOrderId(orderId).stream().filter(product -> product.getId() == productId).findFirst().orElseThrow(() -> new EntityNotFoundException());
    }

    public void deleteProductFromOrder(int orderId, int productId) {
        Order order = super.findById(orderId);
        List<Product> products = order.getItems();
        products.remove(findProductByOrderIdItemId(orderId, productId));
        order.setItems(products);
        super.save(order);
    }

    public void cancelOrder(int id) {
        Order order = super.findById(id);
        order.setStatus(Status.CANCELLED);
        super.save(order);
    }
}
