package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;

import java.util.List;

public interface ServiceCounterService {

    ServiceCounter add(ServiceCounter serviceCounter);

    List<ServiceCounter> getActiveCounters();

    ServiceCounter getById(Long id);
}
