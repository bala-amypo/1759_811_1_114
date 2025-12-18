package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity
public class ServiceCounterEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countername;

    @Column(nullable=false)
    private String department;

    @Column(nullable=false)
    private Boolean isActive;

  

    public ServiceCounterEntity(Long id, String countername, String department, Boolean isActive) {
        this.id = id;
        this.countername = countername;
        this.department = department;
        this.isActive= isActive;
        
    }

    public ServiceCounterEntity() {
        
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public void setCounterName(String name) {
        this.countername = countername;
    }

    public String getCounteName() {
        return this.countername;
    }

    public void setDepartment(String Department) {
        this.department =department;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive =isActive;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }
    
}
