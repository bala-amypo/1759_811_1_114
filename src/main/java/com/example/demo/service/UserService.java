package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

    User register(User user);  // used in t8, t9, t25, t48

    User findByEmail(String email);  // used in t53
}
