package com.paymybuddy.fund_transfer.controller;

import com.paymybuddy.fund_transfer.domain.RoleType;
import com.paymybuddy.fund_transfer.domain.User;
import com.paymybuddy.fund_transfer.service.RoleTypeService;
import com.paymybuddy.fund_transfer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    private RoleTypeService roleTypeService;

    @Autowired
    public UserController(UserService userService, RoleTypeService roleTypeService) {
        this.userService = userService;
        this.roleTypeService = roleTypeService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/getUserById")
    public User getUserById(@RequestParam int id) {
        return userService.findUserById(id);
    }

    @GetMapping("/getUserByEmail")
    public User getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user, HttpServletResponse response) {
        response.setStatus(201);
        return userService.createUser(user);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User user, HttpServletResponse response) {
        userService.updateUser(user);
        response.setStatus(200);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam int id, HttpServletResponse response) {
        userService.deleteUser(id);
        response.setStatus(200);
    }


    // Role Type Methods

    @GetMapping("/roleTypes")
    public List<RoleType> getAllRoleTypes() {
        return roleTypeService.findAllRoleTypes();
    }

    @GetMapping("/getRoleTypeById")
    public RoleType getRoleTypeById(@RequestParam int id) {
        return roleTypeService.findRoleTypeById(id);
    }

    @GetMapping("/getRoleTypeByRoleType")
    public RoleType getRoleTypeByRoleType(@RequestParam String roleType) {
        return roleTypeService.findRoleTypeByRoleType(roleType);
    }

    @PostMapping("/roleType")
    public RoleType createRoleType(@RequestBody RoleType roleType, HttpServletResponse response) {
        response.setStatus(201);
        return roleTypeService.createRoleType(roleType);
    }

    @PutMapping("/roleType")
    public void updateRoleType(@RequestBody RoleType roleType, HttpServletResponse response) {
        roleTypeService.updateRoleType(roleType);
        response.setStatus(200);
    }

    @DeleteMapping("/roleType")
    public void deleteRoleType(@RequestParam int id, HttpServletResponse response) {
        roleTypeService.deleteRoleType(id);
        response.setStatus(200);
    }
}
