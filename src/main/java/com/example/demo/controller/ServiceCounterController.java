package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    @Autowired
    private ServiceCounterService counterService;

    // Create a new service counter
    @PostMapping("/create")
    public ResponseEntity<ServiceCounter> createCounter(@RequestBody ServiceCounter counter) {
        ServiceCounter createdCounter = counterService.createCounter(counter);
        return ResponseEntity.ok(createdCounter);
    }

    // Get service counter by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCounter> getCounter(@PathVariable Long id) {
        ServiceCounter counter = counterService.getCounterById(id);
        return ResponseEntity.ok(counter);
    }

    // Get all service counters
    @GetMapping("/all")
    public ResponseEntity<List<ServiceCounter>> getAllCounters() {
        List<ServiceCounter> counters = counterService.getAllCounters();
        return ResponseEntity.ok(counters);
    }

    // Update counter details
    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceCounter> updateCounter(@PathVariable Long id, @RequestBody ServiceCounter counter) {
        ServiceCounter updatedCounter = counterService.updateCounter(id, counter);
        return ResponseEntity.ok(updatedCounter);
    }

    // Delete counter
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCounter(@PathVariable Long id) {
        counterService.deleteCounter(id);
        return ResponseEntity.ok().build();
    }
}