package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    @Autowired
    private ServiceCounterRepository repository;

    @Override
    public ServiceCounter createCounter(ServiceCounter counter) {
        return repository.save(counter);
    }

    @Override
    public List<ServiceCounter> getAllCounters() {
        return repository.findAll();
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repository.findByIsActiveTrue();
    }
}