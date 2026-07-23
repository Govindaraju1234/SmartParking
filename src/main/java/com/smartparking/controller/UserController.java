package com.smartparking.controller;

import com.smartparking.model.User;
import com.smartparking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
       
    }

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get User By ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // Update User
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id,
                           @RequestBody User user) {

        return userService.updateUser(id, user);
    }
 @GetMapping("/test")
public String test() {
    return "Controller is working";
}
    // Delete User
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);

        return "User deleted successfully.";
    }
} 