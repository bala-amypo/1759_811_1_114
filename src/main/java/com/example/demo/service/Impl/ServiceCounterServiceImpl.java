package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;

import java.util.List;

public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository repository;

    public ServiceCounterServiceImpl(ServiceCounterRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter counter) {
        return repository.save(counter);
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repository.findByIsActiveTrue();
    }
}
