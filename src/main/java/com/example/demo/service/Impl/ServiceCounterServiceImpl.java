package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounterEntity;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository repository;

    public ServiceCounterServiceImpl(ServiceCounterRepository repository) {
        this.repository = repository;
    }

    public ServiceCounterEntity addCounter(ServiceCounterEntity counter) {
        return repository.save(counter);
    }

    public List<ServiceCounterEntity> getActiveCounters() {
        return repository.findByIsActiveTrue();
    }
}