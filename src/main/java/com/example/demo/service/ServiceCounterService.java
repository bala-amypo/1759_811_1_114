package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ServiceCounter;

public interface ServiceCounterService {

    public ServiceCounter createCounter(ServiceCounter counter);

    public List<ServiceCounter> getAllCounters();

    public List<ServiceCounter> getActiveCounters();

  
}