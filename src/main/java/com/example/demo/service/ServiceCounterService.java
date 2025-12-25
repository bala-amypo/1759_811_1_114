package com.example.project.service;

import com.example.project.entity.ServiceCounter;
import java.util.List;

public interface ServiceCounterService {

    ServiceCounter saveServiceCounter(ServiceCounter serviceCounter);

    List<ServiceCounter> getAllServiceCounter();

    ServiceCounter getServiceCounterById(Long serviceCounterId);

    ServiceCounter updateServiceCounter(ServiceCounter serviceCounter);

    void deleteServiceCounter(Long serviceCounterId);
}
