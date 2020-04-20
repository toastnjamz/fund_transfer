package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("user/{id}")
    public User getUserById(@RequestParam int id) {
        return userService.findUserById(id);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user, HttpServletResponse response) {
        response.setStatus(201);
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public void updateUser(@Valid @RequestBody User user, HttpServletResponse response) {
        userService.updateUser(user);
        response.setStatus(200);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam int id, HttpServletResponse response) {
        userService.deleteUser(id);
        response.setStatus(200);
    }
}
