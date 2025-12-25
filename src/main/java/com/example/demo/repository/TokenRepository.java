package com.example.demo.repository;

import com.example.demo.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenNumber(String tokenNumber);

    List<Token> findByServiceCounterIdAndStatusOrderByIssuedAtAsc(Long serviceCounterId, String status);
}