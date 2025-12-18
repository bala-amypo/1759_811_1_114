package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService{

    @Autowired
    ServiceCounterRepository repo;

    @Override
    public ServiceCounter createCounter(ServiceCounter counter) {
        return repo.save(counter);
    }

    @Override
    public List<ServiceCounter> getAllCounters() {
        return repo.findAll();
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return repo.findByIsActiveTrue();
    }

}