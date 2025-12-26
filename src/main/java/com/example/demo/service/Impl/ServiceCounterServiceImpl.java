// ServiceCounterServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;

import java.util.List;

public class ServiceCounterServiceImpl implements ServiceCounterService {
    private final ServiceCounterRepository repo;

    public ServiceCounterServiceImpl(ServiceCounterRepository repo) {
        this.repo = repo;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter sc) {
        return repo.save(sc);
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repo.findByIsActiveTrue();
    }
}
