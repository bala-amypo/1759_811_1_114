package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_logs")
public class TokenLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many logs can belong to one Token
    @ManyToOne
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;

    @Column(nullable = false)
    private String logMessage;

    @Column(nullable = false)
    private LocalDateTime loggedAt;

    // Default constructor
    public TokenLog() {
        this.loggedAt = LocalDateTime.now();
    }

    // Parameterized constructor
    public TokenLog(Token token, String logMessage) {
        this.token = token;
        this.logMessage = logMessage;
        this.loggedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }
}
