package com.example.demo.repository;

import com.example.demo.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<QueueEntity, Long> {

    Optional<QueueEntity> findByTokenId(Long tokenId);
}