// src/main/java/com/example/demo/controller/ServiceCounterController.java
package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.impl.ServiceCounterServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counters")
public class ServiceCounterController {

    private final ServiceCounterServiceImpl counterService;

    // Constructor injection: tests instantiate ServiceCounterServiceImpl directly,
    // but here we wire it with the repository bean.
    public ServiceCounterController(ServiceCounterRepository repo) {
        this.counterService = new ServiceCounterServiceImpl(repo);
    }

    /**
     * Add a new service counter.
     * Example: POST /api/counters
     * Body: { "counterName": "C1", "department": "Cardio" }
     */
    @PostMapping
    public ServiceCounter addCounter(@RequestBody ServiceCounter sc) {
        return counterService.addCounter(sc);
    }

    /**
     * Get all active counters.
     * Example: GET /api/counters/active
     */
    @GetMapping("/active")
    public List<ServiceCounter> getActiveCounters() {
        return counterService.getActiveCounters();
    }
}
