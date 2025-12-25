package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_positions")
public class QueuePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One with Token
    @OneToOne
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor
    public QueuePosition() {
        this.updatedAt = LocalDateTime.now();
    }

    // Parameterized constructor
    public QueuePosition(Token token, Integer position) {
        this.token = token;
        this.position = position;
        this.updatedAt = LocalDateTime.now();
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
