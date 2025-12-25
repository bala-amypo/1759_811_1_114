package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_logs")
public class TokenLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;

    private String logMessage;

    private LocalDateTime loggedAt;

    // No-arg constructor
    public TokenLog() {
    }

    // Parameterized constructor
    public TokenLog(Token token, String logMessage) {
        this.token = token;
        this.logMessage = logMessage;
        this.loggedAt = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        this.loggedAt = LocalDateTime.now();
    }

    // Getters & setters
    public Long getId() {
        return id;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}