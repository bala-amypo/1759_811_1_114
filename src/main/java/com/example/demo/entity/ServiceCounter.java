package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_counters")
public class ServiceCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String counterName;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Boolean isActive;

    // One ServiceCounter can have many tokens
    @OneToMany(mappedBy = "serviceCounter", cascade = CascadeType.ALL)
    private List<Token> tokens;

    // Default constructor
    public ServiceCounter() {
        this.isActive = true; // Default active
    }

    // Parameterized constructor
    public ServiceCounter(String counterName, String department, Boolean isActive) {
        this.counterName = counterName;
        this.department = department;
        this.isActive = isActive != null ? isActive : true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
