package com.example.demo.repository;

import com.example.demo.model.QueuePosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueuePositionRepository extends JpaRepository<QueuePosition, Long> {

    Optional<QueuePosition> findByToken_Id(Long tokenId);
}
