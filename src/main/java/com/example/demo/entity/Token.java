package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tokenNumber;

    @ManyToOne
    @JoinColumn(name = "service_counter_id", nullable = false)
    private ServiceCounter serviceCounter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    private LocalDateTime completedAt;

    // One-to-One with QueuePosition
    @OneToOne(mappedBy = "token", cascade = CascadeType.ALL)
    private QueuePosition queuePosition;

    // One-to-Many with TokenLog
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL)
    private List<TokenLog> tokenLogs;

    // Status Enum
    public enum Status {
        WAITING,
        SERVING,
        COMPLETED,
        CANCELLED
    }

    // Default constructor
    public Token() {
        this.status = Status.WAITING;
        this.issuedAt = LocalDateTime.now();
    }

    // Parameterized constructor
    public Token(String tokenNumber, ServiceCounter serviceCounter, Status status) {
        this.tokenNumber = tokenNumber;
        this.serviceCounter = serviceCounter;
        this.status = status != null ? status : Status.WAITING;
        this.issuedAt = LocalDateTime.now();
    }

    // Getters and Setters
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

    public ServiceCounter getServiceCounter() {
        return serviceCounter;
    }

    public void setServiceCounter(ServiceCounter serviceCounter) {
        this.serviceCounter = serviceCounter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public QueuePosition getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(QueuePosition queuePosition) {
        this.queuePosition = queuePosition;
    }

    public List<TokenLog> getTokenLogs() {
        return tokenLogs;
    }

    public void setTokenLogs(List<TokenLog> tokenLogs) {
        this.tokenLogs = tokenLogs;
    }
}
