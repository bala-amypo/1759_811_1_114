package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class Token {

    public enum Status {
        WAITING, SERVING, COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer tokenNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private ServiceCounter counter;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(Integer tokenNumber) { this.tokenNumber = tokenNumber; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public ServiceCounter getCounter() { return counter; }
    public void setCounter(ServiceCounter counter) { this.counter = counter; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
