package com.example.demo.controller;

import com.example.demo.entity.ServiceCounterEntity;
import com.example.demo.service.ServiceCounterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
@Tag(name = "Service Counter API")
public class ServiceCounterController {

    private final ServiceCounterService service;

    public ServiceCounterController(ServiceCounterService service) {
        this.service = service;
    }

    @PostMapping
    public ServiceCounterEntity add(@RequestBody ServiceCounterEntity counter) {
        return service.addCounter(counter);
    }

    @GetMapping("/active")
    public List<ServiceCounterEntity> active() {
        return service.getActiveCounters();
    }
}