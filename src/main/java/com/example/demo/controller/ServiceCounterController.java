
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

    public ServiceCounterController(ServiceCounterRepository repo) {
        this.counterService = new ServiceCounterServiceImpl(repo);
    }

    @PostMapping
    public ServiceCounter add(@RequestBody ServiceCounter sc) {
        return counterService.addCounter(sc);
    }

    @GetMapping("/active")
    public List<ServiceCounter> active() {
        return counterService.getActiveCounters();
    }
}
