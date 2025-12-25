package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User login(String username, String password);
    User register(User user);
    void logout(Long userId);
}