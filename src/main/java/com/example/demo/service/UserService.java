package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findByEmail(String email);
    boolean checkPassword(String rawPassword, String encodedPassword);
}
