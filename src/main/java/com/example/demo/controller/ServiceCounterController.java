package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    @Autowired
    private ServiceCounterService serviceCounterService;

    
    @PostMapping
    public ServiceCounter createCounter(@RequestBody ServiceCounter counter) {
        return serviceCounterService.createCounter(counter);
    }

   
    @GetMapping
    public List<ServiceCounter> getAllCounters() {
        return serviceCounterService.getAllCounters();
    }

 
    @GetMapping("/active")
    public List<ServiceCounter> getActiveCounters() {
        return serviceCounterService.getActiveCounters();
    }
}