package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TokenLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "token_id")
    private Token token;

    private String logMessage;

    private LocalDateTime loggedAt;

    @PrePersist
    public void onCreate() {
        this.loggedAt = LocalDateTime.now();
    }

    public TokenLog() {
    }

    public TokenLog(Token token, String logMessage) {
        this.token = token;
        this.logMessage = logMessage;
    }



    public Long getId() {
        return id;
    }

    public Token getToken() {
        return token;
    }

    public void setId(Long id) {
        this.id = id;
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
}
