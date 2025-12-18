package com.example.demo.service;

import com.example.demo.entity.ServiceCounterEntity;
import java.util.List;

public interface ServiceCounterService {
    ServiceCounterEntity addCounter(ServiceCounterEntity counter);
    List<ServiceCounterEntity> getActiveCounters();
}