package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.OrderDto;
import com.epam.clothshopapp.mapper.dto.UserGetDto;
import com.epam.clothshopapp.mapper.dto.UserPostDto;
import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.User;
import com.epam.clothshopapp.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Api(tags = {SwaggerConfig.USER})
public class UserController {

    private UserService userService;
    private DtoMapper dtoMapper;

    @Autowired
    public UserController(UserService userService, DtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity findAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            List<UserGetDto> userGetDtos = users.stream().map(user -> dtoMapper.userToUserGetDto(user)).collect(Collectors.toList());
            return new ResponseEntity<>(userGetDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody UserPostDto userPostDto) {
        try {
            userService.saveUser(dtoMapper.userPostDtoToUser(userPostDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findUserById(@PathVariable("id") int id) {
        try {
            Optional<User> user = userService.findUserById(id);
            if (user.isPresent()) {
                return new ResponseEntity<>(dtoMapper.userToUserGetDto(user.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateUserById(@PathVariable("id") int id, @RequestBody UserPostDto userPostDto) {
        try {
            userService.updateUserById(id, dtoMapper.userPostDtoToUser(userPostDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") int id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/orders")
    public ResponseEntity findOrdersByUserId(@PathVariable("id") int id) {
        try {
            List<Order> orders = userService.findOrdersByUserId(id);
            List<OrderDto> orderDtos = orders.stream().map(order -> dtoMapper.orderToOrderDto(order)).collect(Collectors.toList());
            return new ResponseEntity<>(orderDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{id}/orders")
    public ResponseEntity saveOrderToUser(@PathVariable("id") int id, @RequestBody OrderDto orderDto) {
        try {
            userService.saveOrderToUser(id, dtoMapper.orderDtoToOrder(orderDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
