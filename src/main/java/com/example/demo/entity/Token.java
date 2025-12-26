package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenNumber;

    private String status; // WAITING, SERVING, COMPLETED, CANCELLED

    @ManyToOne
    private ServiceCounter serviceCounter;

    private LocalDateTime issuedAt;     // token issued time
    private LocalDateTime completedAt;  // token completed/cancelled time

    private String role; // user role for JWT claim if needed

    // ----------------------
    // Constructors
    // ----------------------
    public Token() {
        this.issuedAt = LocalDateTime.now();
        this.status = "WAITING";
    }

    // ----------------------
    // Getters & Setters
    // ----------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(String tokenNumber) { this.tokenNumber = tokenNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public ServiceCounter getServiceCounter() { return serviceCounter; }
    public void setServiceCounter(ServiceCounter serviceCounter) { this.serviceCounter = serviceCounter; }

    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
