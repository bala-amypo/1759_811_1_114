// src/main/java/com/example/demo/service/ServiceCounterService.java
package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import java.util.List;

public interface ServiceCounterService {
    ServiceCounter addCounter(ServiceCounter sc);
    List<ServiceCounter> getActiveCounters();
}
