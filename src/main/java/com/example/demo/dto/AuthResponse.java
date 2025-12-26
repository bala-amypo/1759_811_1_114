package com.example.demo.dto;

import com.example.demo.entity.User;

public class AuthResponse {

    private String token;
    private Long userId;
    private String email;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(String token, User user) {
        this.token = token;
        this.userId = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    // -------- Getters & Setters --------
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}
