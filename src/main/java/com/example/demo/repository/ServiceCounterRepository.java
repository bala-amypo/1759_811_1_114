package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StudentEntity;
import java.util.List;

@Repository
public interface ServiceCounterRepository extends JpaRepository<ServiceCounter, Long>{

    List<ServiceCounter>findByIsActiveTrue();

}