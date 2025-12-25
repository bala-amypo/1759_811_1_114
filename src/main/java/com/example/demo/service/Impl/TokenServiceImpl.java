package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.entity.Token.Status;
import com.example.demo.entity.TokenLog;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueuePositionRepository queueRepository;

    // Constructor Injection
    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogRepository tokenLogRepository,
                            QueuePositionRepository queueRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queueRepository = queueRepository;
    }

    @Override
    public Token issueToken(Long counterId) throws Exception {
        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new Exception("Counter not found"));

        if (!counter.getIsActive()) {
            throw new Exception("Counter not active");
        }

        // Generate token number
        String tokenNumber = "T" + System.currentTimeMillis();

        Token token = new Token(tokenNumber, counter, Status.WAITING);
        Token savedToken = tokenRepository.save(token);

        // Create queue position
        int position = queueRepository.findByToken_Id(savedToken.getId()) != null ? 
                       queueRepository.findByToken_Id(savedToken.getId()).getPosition() : 1;
        QueuePosition queuePosition = new QueuePosition(savedToken, position);
        queueRepository.save(queuePosition);

        // Create token log
        TokenLog log = new TokenLog(savedToken, "Token issued");
        tokenLogRepository.save(log);

        return savedToken;
    }

    @Override
    public Token updateTokenStatus(Long tokenId, Status newStatus) throws Exception {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new Exception("Token not found"));

        // Validate status transition
        Status current = token.getStatus();
        if (!isValidTransition(current, newStatus)) {
            throw new Exception("Invalid status transition");
        }

        token.setStatus(newStatus);
        if (newStatus == Status.COMPLETED || newStatus == Status.CANCELLED) {
            token.setCompletedAt(LocalDateTime.now());
        }
        tokenRepository.save(token);

        // Log status change
        TokenLog log = new TokenLog(token, "Status changed to " + newStatus);
        tokenLogRepository.save(log);

        return token;
    }

    @Override
    public Token getTokenById(Long tokenId) throws Exception {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new Exception("Token not found"));
        return token;
    }

    @Override
    public List<Token> getTokensByCounterAndStatus(Long counterId, Status status) {
        return tokenRepository.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, status);
    }

    // Helper: validate status transitions
    private boolean isValidTransition(Status current, Status next) {
        switch (current) {
            case WAITING:
                return next == Status.SERVING || next == Status.CANCELLED;
            case SERVING:
                return next == Status.COMPLETED || next == Status.CANCELLED;
            case COMPLETED:
            case CANCELLED:
                return false;
            default:
                return false;
        }
    }
}
