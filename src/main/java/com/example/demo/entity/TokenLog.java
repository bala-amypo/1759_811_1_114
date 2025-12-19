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
    @JoinColumn(name = "token_id")
    private Token token;

    private String logMessage;

    private LocalDateTime loggedAt;

    @PrePersist
    public void prePersist() {
        loggedAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====
   

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }

    public String getLogMessage() { return logMessage; }
    public void setLogMessage(String logMessage) { this.logMessage = logMessage; }

    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}
