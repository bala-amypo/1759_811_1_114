package com.example.demo.service.Impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final QueuePositionRepository queuePositionRepository;
    private final TokenLogRepository tokenLogRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            QueuePositionRepository queuePositionRepository,
                            TokenLogRepository tokenLogRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.queuePositionRepository = queuePositionRepository;
        this.tokenLogRepository = tokenLogRepository;
    }

    @Override
    @Transactional
    public Token generateToken(Long counterId, int position) {
        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        Token token = new Token();
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());
        token.setServiceCounter(counter);

        tokenRepository.save(token);

        QueuePosition queuePosition = new QueuePosition(token, position);
        queuePositionRepository.save(queuePosition);

        TokenLog log = new TokenLog(token, "Token Issued");
        tokenLogRepository.save(log);

        return token;
    }

    @Override
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }

    @Override
    @Transactional
    public Token updateTokenStatus(Long tokenId, String status) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        // Only allow valid transitions
        if (token.getStatus().equals("WAITING") && status.equals("SERVING") ||
            token.getStatus().equals("SERVING") && status.equals("COMPLETED") ||
            status.equals("CANCELLED")) {

            token.setStatus(status);

            if (status.equals("COMPLETED")) {
                token.setCompletedAt(LocalDateTime.now());
            }

            tokenRepository.save(token);

            TokenLog log = new TokenLog(token, "Status updated to " + status);
            tokenLogRepository.save(log);
        } else {
            throw new RuntimeException("Invalid status transition");
        }

        return token;
    }
}
