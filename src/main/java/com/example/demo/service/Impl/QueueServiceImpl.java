package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.repository.QueuePositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueServiceImpl {

    private final QueuePositionRepository repository;

    public QueueServiceImpl(QueuePositionRepository repository) {
        this.repository = repository;
    }

    public List<QueuePosition> getQueue() {
        return repository.findAll();
    }
}
