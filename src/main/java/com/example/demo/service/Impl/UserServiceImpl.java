package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // REQUIRED constructor signature
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            // MUST contain "Email"
            throw new DuplicateResourceException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User login(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        // MUST contain "not found"
                        new ResourceNotFoundException("User not found"));
    }
}
