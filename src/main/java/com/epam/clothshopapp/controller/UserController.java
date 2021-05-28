package com.epam.clothshopapp.controller;

import com.epam.clothshopapp.config.SwaggerConfig;
import com.epam.clothshopapp.mapper.DtoMapper;
import com.epam.clothshopapp.mapper.dto.LoginDto;
import com.epam.clothshopapp.mapper.dto.OrderDto;
import com.epam.clothshopapp.mapper.dto.UserGetDto;
import com.epam.clothshopapp.mapper.dto.UserPostDto;
import com.epam.clothshopapp.model.Order;
import com.epam.clothshopapp.model.User;
import com.epam.clothshopapp.security.JwtTokenProvider;
import com.epam.clothshopapp.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Api(tags = {SwaggerConfig.USER})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DtoMapper dtoMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
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
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity saveUser(@RequestBody UserPostDto userPostDto) {
        try {
            userService.saveUser(dtoMapper.userPostDtoToUser(userPostDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
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
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity updateUserById(@PathVariable("id") int id, @RequestBody UserPostDto userPostDto) {
        try {
            userService.updateUserById(id, dtoMapper.userPostDtoToUser(userPostDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('WRITE')")
    public ResponseEntity deleteUserById(@PathVariable("id") int id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}/orders")
    @PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
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
    @PreAuthorize("hasAnyAuthority('READ', 'WRITE')")
    public ResponseEntity saveOrderToUser(@PathVariable("id") int id, @RequestBody OrderDto orderDto) {
        try {
            userService.saveOrderToUser(id, dtoMapper.orderDtoToOrder(orderDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = userService.findUserByUsername(loginDto.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("User does not exists");
            }
            String token = jwtTokenProvider.createToken(loginDto.getUsername(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", loginDto.getUsername());
            response.put("token", token);
            response.put("permission", user.getRole().getPermissions());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Unable to login, check email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
