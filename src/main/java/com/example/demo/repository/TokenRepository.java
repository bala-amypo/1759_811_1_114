package com.example.demo.repository;

import com.example.demo.entity.Token;
import com.example.demo.entity.Token.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(Long counterId, Status status);

    Token findByTokenNumber(String tokenNumber);
}
