package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class ServiceCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String counterName;

    private String department;

    private Boolean isActive = true;

    public ServiceCounter() {
    }

    public ServiceCounter(String counterName, String department, Boolean isActive) {
        this.counterName = counterName;
        this.department = department;
        this.isActive = isActive;
    }

    // -------- Getters & Setters --------

    public Long getId() {
        return id;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
