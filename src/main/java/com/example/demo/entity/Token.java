package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "tokens",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "tokenNumber")
    }
)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tokenNumber;

    @Column(nullable = false)
    private String status = "WAITING";

    @ManyToOne
    @JoinColumn(name = "counter_id")
    private ServiceCounter serviceCounter;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    private LocalDateTime completedAt;

    // --------------------
    // Auto timestamp
    // --------------------
    @PrePersist
    protected void onCreate() {
        this.issuedAt = LocalDateTime.now();
    }

    // --------------------
    // Getters & Setters
    // --------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceCounter getServiceCounter() {
        return serviceCounter;
    }

    public void setServiceCounter(ServiceCounter serviceCounter) {
        this.serviceCounter = serviceCounter;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
