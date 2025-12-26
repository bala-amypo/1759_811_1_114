package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    private final ServiceCounterService counterService;

    public ServiceCounterController(ServiceCounterService counterService) {
        this.counterService = counterService;
    }

    // Add a new service counter
    @PostMapping("/add")
    public ServiceCounter addCounter(@RequestParam String counterName,
                                     @RequestParam String department) {
        ServiceCounter sc = new ServiceCounter();
        sc.setCounterName(counterName);
        sc.setDepartment(department);
        return counterService.addCounter(sc);
    }

    // Get all active counters
    @GetMapping("/active")
    public List<ServiceCounter> getActiveCounters() {
        return counterService.getActiveCounters();
    }
}
