package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import java.util.List;
import java.util.Optional;

public interface ServiceCounterService {

    ServiceCounter createCounter(ServiceCounter counter);
    ServiceCounter updateCounter(Long id, ServiceCounter counter);
    Optional<ServiceCounter> getCounterById(Long id);
    List<ServiceCounter> getAllCounters();
    void deleteCounter(Long id);
}