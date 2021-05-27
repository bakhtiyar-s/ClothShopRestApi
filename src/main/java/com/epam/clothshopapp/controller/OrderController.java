package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.OrderDto;
import com.epam.clothshopapp.mapper.dto.ProductDto;
import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.Product;
import com.epam.clothshopapp.service.OrderService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@Api(tags = {SwaggerConfig.ORDER})
public class OrderController {

    private OrderService orderService;
    private DtoMapper dtoMapper;

    @Autowired
    public OrderController(OrderService orderService, DtoMapper dtoMapper) {
        this.orderService = orderService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity findAllOrders() {
        try {
            List<Order> orders = orderService.findAllOrders();
            List<OrderDto> orderDtos = orders.stream().map(order -> dtoMapper.orderToOrderDto(order)).collect(Collectors.toList());
            return new ResponseEntity<>(orderDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findOrderById(@PathVariable("id") int id) {
        try {
            Optional<Order> order = orderService.findOrderById(id);
            if (order.isPresent()) {
                return new ResponseEntity<>(dtoMapper.orderToOrderDto(order.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteOrderById(@PathVariable("id") int id) {
        try {
            orderService.deleteOrderById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/items")
    public ResponseEntity findProductsByOrderId(@PathVariable("id") int id) {
        try {
            List<Product> products = orderService.findProductsByOrderId(id);
            List<ProductDto> productDtos = products.stream().map(product -> dtoMapper.productToProductDto(product)).collect(Collectors.toList());
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{id}/items")
    public ResponseEntity saveProductToOrder(@PathVariable("id") int id, @RequestBody ProductDto productDto) {
        try {
            orderService.saveProductToOrder(id, dtoMapper.productDtoToProduct(productDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{oid}/items/{iid}")
    public ResponseEntity findProductByOrderIdItemId(@PathVariable("oid") int oid, @PathVariable("iid") int iid) {
        try {
            Optional<Product> product = orderService.findProductByOrderIdItemId(oid, iid);
            if (product.isPresent()) {
                return new ResponseEntity<>(dtoMapper.productToProductDto(product.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
