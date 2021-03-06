package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.OrderDto;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.OrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@Api(tags = {SwaggerConfig.ORDER})
@PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DtoMapper<Order, OrderDto> orderMapper;
    private final DtoMapper<Product, ProductDto> productMapper;

    @GetMapping
    public ResponseEntity findAllOrders() {
        try {
            List<Order> orders = orderService.findAll();
            List<OrderDto> orderDtos = orders.stream().map(order -> orderMapper.toDto(order)).collect(Collectors.toList());
            return new ResponseEntity<>(orderDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findOrderById(@PathVariable("id") int id) {
        try {
            Order order = orderService.findById(id);
            return new ResponseEntity<>(orderMapper.toDto(order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteOrderById(@PathVariable("id") int id) {
        try {
            orderService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/items")
    public ResponseEntity findProductsByOrderId(@PathVariable("id") int id) {
        try {
            List<Product> products = orderService.findProductsByOrderId(id);
            List<ProductDto> productDtos = products.stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{id}/items")
    public ResponseEntity saveProductToOrder(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        try {
            orderService.saveProductToOrder(id, productMapper.fromDto(productDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{oid}/items/{iid}")
    public ResponseEntity findProductByOrderIdItemId(@PathVariable("oid") int oid, @PathVariable("iid") int iid) {
        try {
            Product product = orderService.findProductByOrderIdItemId(oid, iid);
            return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{oid}/items/{iid}")
    public ResponseEntity deleteProductFromOrder(@PathVariable("oid") int oid, @PathVariable("iid") int iid) {
        try {
            orderService.deleteProductFromOrder(oid, iid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{id}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("id") int id) {
        try {
            orderService.cancelOrder(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
