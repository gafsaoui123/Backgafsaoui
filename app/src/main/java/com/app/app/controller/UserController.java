package com.app.app.controller;

import com.app.app.entity.User;
import com.app.app.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin() {
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser() {
        return "This URL is only accessible to the user";
    }

    @GetMapping("/users/connected-today")
    @PreAuthorize("hasRole('Admin')")
    public long getUsersConnectedToday() {
        return userService.getUsersConnectedToday();
    }

    @GetMapping("/users/connections")
    @PreAuthorize("hasRole('Admin')")
    public Map<String, Long> getUserConnections() {
        return userService.getUserConnections();
    }

    @GetMapping("/users/categories")
    @PreAuthorize("hasRole('Admin')")
    public Map<String, Long> getUserCategories() {
        return userService.getUserCategories();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}