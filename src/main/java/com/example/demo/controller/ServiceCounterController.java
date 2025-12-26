package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    private final ServiceCounterService counterService;

    public ServiceCounterController(ServiceCounterService counterService) {
        this.counterService = counterService;
    }

    // Add a new counter
    @PostMapping("/add")
    public ResponseEntity<ServiceCounter> addCounter(@RequestBody ServiceCounter counter) {
        ServiceCounter saved = counterService.addCounter(counter);
        return ResponseEntity.ok(saved);
    }

    // Get all active counters
    @GetMapping("/active")
    public ResponseEntity<List<ServiceCounter>> getActiveCounters() {
        List<ServiceCounter> list = counterService.getActiveCounters();
        return ResponseEntity.ok(list);
    }
}
