package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.service.ServiceCounterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Service Counters")
@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    private final ServiceCounterService serviceCounterService;

    public ServiceCounterController(ServiceCounterService serviceCounterService) {
        this.serviceCounterService = serviceCounterService;
    }

    @Operation(summary = "Add a new service counter")
    @PostMapping("/")
    public ServiceCounter addCounter(@Valid @RequestBody ServiceCounter serviceCounter) {
        return serviceCounterService.add(serviceCounter);
    }

    @Operation(summary = "Get all active service counters")
    @GetMapping("/active")
    public List<ServiceCounter> getActiveCounters() {
        return serviceCounterService.getActiveCounters();
    }
}
