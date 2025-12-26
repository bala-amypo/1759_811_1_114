package com.example.demo.service.impl;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.ServiceCounterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService {

    private final ServiceCounterRepository counterRepo;

    public ServiceCounterServiceImpl(ServiceCounterRepository counterRepo) {
        this.counterRepo = counterRepo;
    }

    @Override
    public ServiceCounter addCounter(ServiceCounter counter) {
        return counterRepo.save(counter);
    }

    @Override
    public List<ServiceCounter> getActiveCounters() {
        return counterRepo.findByIsActiveTrue();
    }
}
