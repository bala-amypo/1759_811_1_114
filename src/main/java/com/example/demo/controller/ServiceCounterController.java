package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    private final ServiceCounterService counterService;

    // Constructor Injection
    public ServiceCounterController(ServiceCounterService counterService) {
        this.counterService = counterService;
    }

    // Add a new counter
    @PostMapping("/")
    public ResponseEntity<?> addCounter(@RequestBody ServiceCounter counter) {
        try {
            ServiceCounter added = counterService.addCounter(counter);
            return ResponseEntity.ok(added);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // List all active counters
    @GetMapping("/active")
    public ResponseEntity<List<ServiceCounter>> getActiveCounters() {
        List<ServiceCounter> counters = counterService.getActiveCounters();
        return ResponseEntity.ok(counters);
    }
}
