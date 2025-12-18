package com.example.demo.repository;

import com.example.demo.entity.TokenLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenLogRepository extends JpaRepository<TokenLogEntity, Long> {

    List<TokenLogEntity> findByTokenId(Long tokenId);
}