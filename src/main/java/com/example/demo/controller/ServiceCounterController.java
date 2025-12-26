package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.impl.ServiceCounterServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    private final ServiceCounterServiceImpl counterService;

    public ServiceCounterController(ServiceCounterServiceImpl counterService) {
        this.counterService = counterService;
    }

    @PostMapping("/add")
    public ServiceCounter addCounter(@RequestBody ServiceCounter counter) {
        return counterService.addCounter(counter);
    }

    @GetMapping("/active")
    public List<ServiceCounter> getActiveCounters() {
        return counterService.getActiveCounters();
    }
}
