package com.example.demo.repository;

import com.example.demo.entity.QueuePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueuePositionRepository extends JpaRepository<QueuePosition, Long> {
    List<QueuePosition> findByQueueIdOrderByPositionAsc(Long queueId);
}
