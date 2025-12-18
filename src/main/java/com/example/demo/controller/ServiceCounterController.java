package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;


@RestController
public class ServiceCounterController {

    @Autowired
    ServiceCounterService ser;

    @PostMapping
    public ServiceCounter createCounter(@RequestBody ServiceCounter counter){
        return ser.createCounter(counter);
    }
    
    @GetMapping
    public List<ServiceCounter> getAllCounters() {
        return ser.getAllCounters();
    }
    
    @GetMapping("/active")
    public List<SserviceCounter> getActiveCounters() {
        return ser.getActiveCounters();
    }

    
}