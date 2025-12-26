package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class QueuePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Token token;

    private int position;

    public QueuePosition() {}

    public QueuePosition(Token token, int position) {
        this.token = token;
        this.position = position;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}
