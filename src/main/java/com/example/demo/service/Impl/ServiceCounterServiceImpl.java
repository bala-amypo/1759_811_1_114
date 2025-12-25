package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository counterRepository;

    // Constructor Injection
    public ServiceCounterServiceImpl(ServiceCounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter counter) throws Exception {
        if (counter.getCounterName() == null || counter.getCounterName().isEmpty()) {
            throw new Exception("Counter name is required");
        }
        if (counter.getDepartment() == null || counter.getDepartment().isEmpty()) {
            throw new Exception("Department is required");
        }
        if (counter.getIsActive() == null) {
            counter.setIsActive(true); // Default active
        }
        return counterRepository.save(counter);
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return counterRepository.findByIsActiveTrue();
    }

    @Override
    public ServiceCounter getCounterById(Long id) throws Exception {
        ServiceCounter counter = counterRepository.findById(id)
                .orElseThrow(() -> new Exception("not found"));
        if (!counter.getIsActive()) {
            throw new Exception("not active");
        }
        return counter;
    }
}
