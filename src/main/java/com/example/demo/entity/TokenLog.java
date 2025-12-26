package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TokenLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Token token;

    private String action;

    private LocalDateTime loggedAt;

    public TokenLog() {}

    // Constructor for convenience
    public TokenLog(Token token, String action) {
        this.token = token;
        this.action = action;
        this.loggedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Token getToken() { return token; }

    public void setToken(Token token) { this.token = token; }

    public String getAction() { return action; }

    public void setAction(String action) { this.action = action; }

    public LocalDateTime getLoggedAt() { return loggedAt; }

    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}
