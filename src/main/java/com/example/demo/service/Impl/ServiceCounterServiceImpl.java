package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository counterRepository;

    public ServiceCounterServiceImpl(ServiceCounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public ServiceCounter createCounter(ServiceCounter counter) {
        return counterRepository.save(counter);
    }

    @Override
    public ServiceCounter updateCounter(Long id, ServiceCounter counter) {
        Optional<ServiceCounter> existing = counterRepository.findById(id);
        if (existing.isPresent()) {
            ServiceCounter c = existing.get();
            c.setCounterName(counter.getCounterName());
            return counterRepository.save(c);
        }
        return null;
    }

    @Override
    public Optional<ServiceCounter> getCounterById(Long id) {
        return counterRepository.findById(id);
    }

    @Override
    public List<ServiceCounter> getAllCounters() {
        return counterRepository.findAll();
    }

    @Override
    public void deleteCounter(Long id) {
        counterRepository.deleteById(id);
    }
}