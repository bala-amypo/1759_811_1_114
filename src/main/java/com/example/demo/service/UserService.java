package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User register(User user);
    User findByEmail(String email);

    // Needed for login check
    boolean checkPassword(User user, String rawPassword);
}
