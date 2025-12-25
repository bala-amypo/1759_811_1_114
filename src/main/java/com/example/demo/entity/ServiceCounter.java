package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class ServiceCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String counterName;

    // Getters and Setters
    public Long getId() { return id; }

    public String getCounterName() { return counterName; }
    public void setCounterName(String counterName) { this.counterName = counterName; }
}