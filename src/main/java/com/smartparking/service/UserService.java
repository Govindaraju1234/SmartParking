package com.smartparking.service;

import com.smartparking.model.User;
import com.smartparking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

     @Autowired
      private UserRepository userRepository;

    // Register User
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
          user.setRole("USER");
        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By Id
    public User getUserById(Integer id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new RuntimeException("User not found");
    }

    // Update User
    public User updateUser(Integer id, User updatedUser) {

        User user = getUserById(id);

        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());
        user.setPassword(updatedUser.getPassword());

        return userRepository.save(user);
    }

    // Delete User
    public void deleteUser(Integer id) {

        User user = getUserById(id);

        userRepository.delete(user);
    }
    public User findByEmail(String email) {

    return userRepository.findByEmail(email)
            .orElse(null);

}
public User loginUser(String email, String password) {

    User user = userRepository.findByEmail(email).orElse(null);

    if (user == null) {
        return null;
    }

    if (!user.getPassword().equals(password)) {
        return null;
    }

    return user;
}

}