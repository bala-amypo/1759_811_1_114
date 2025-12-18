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
        this.name = name;
        this.email = email;
        this.password= password;
        this.role=role;
    }

    public UserEntity() {
        
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setpasssword(String password) {
        this.password =password;
    }

    public String getpassword() {
        return this.password;
    }
    public void setrole(String role) {
        this.role=role;
    }

    public String getrole() {
        return this.role;
    }
}
