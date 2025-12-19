package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository serviceCounterRepository;

    // Constructor Injection (required)
    public ServiceCounterServiceImpl(ServiceCounterRepository serviceCounterRepository) {
        this.serviceCounterRepository = serviceCounterRepository;
    }

    @Override
    public ServiceCounter add(ServiceCounter serviceCounter) {
        // Default active if not provided
        if (serviceCounter.getIsActive() == null) {
            serviceCounter.setIsActive(true);
        }
        return serviceCounterRepository.save(serviceCounter);
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return serviceCounterRepository.findByIsActiveTrue();
    }

    @Override
    public ServiceCounter getById(Long id) {
        return serviceCounterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service Counter not found"));
    }
}
