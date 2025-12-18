package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ServiceCounter;

public interface ServiceCounterService {

    public ServiceCiunter createCounter(S student);

    public List<StudentEntity> getStudents();

    public StudentEntity getStudentById(Long id);

    public void deleteStudentById(Long id);

}