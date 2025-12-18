package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;

@Service
public class ServiceCounterServiceImpl implements ServiceCounterService{

    @Autowired
    ServiceCounter repo;

    @Override
    public ServiceCounter createCounter(ServiceCounter counter) {
        return repo.save(counter);
    }

    @Override
    public List<ServiceCounter> getAllCounters() {
        return repo.findAll();
    }

    @Override
    public List<StudentEntity> getStudentById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void deleteStudentById(Long id) {
        repo.deleteById(id);
    }

}