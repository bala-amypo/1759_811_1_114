
package com.example.demo.service.Impl;

import com.example.demo.entity.Queue;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.repository.QueueRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueueRepository queueRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository counterRepository,
                            TokenLogRepository tokenLogRepository,
                            QueueRepository queueRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queueRepository = queueRepository;
    }

    @Override
    public Token issueToken(User user, ServiceCounter counter) {
        if (!counter.getIsActive()) {
            throw new RuntimeException("Counter not active");
        }

        long count = tokenRepository.count();
        int nextTokenNumber = (int) count + 1;

        Token token = new Token();
        token.setTokenNumber(nextTokenNumber);
        token.setUser(user);
        token.setCounter(counter);
        token.setStatus(Token.Status.WAITING);
        token.setCreatedAt(LocalDateTime.now());

        Token savedToken = tokenRepository.save(token);

        
        Queue queue = new Queue();
        queue.setToken(savedToken);
        queue.setPosition(1);
        queue.setUpdatedAt(LocalDateTime.now());

        queueRepository.save(queue);

        return savedToken;
    }

    @Override
    public Token updateStatus(Long tokenId, Token.Status newStatus) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        Token.Status currentStatus = token.getStatus();

        if (currentStatus == Token.Status.WAITING && newStatus != Token.Status.SERVING) {
            throw new RuntimeException("invalid status");
        }
        if (currentStatus == Token.Status.SERVING && newStatus != Token.Status.COMPLETED) {
            throw new RuntimeException("invalid status");
        }
        if (currentStatus == Token.Status.COMPLETED) {
            throw new RuntimeException("invalid status");
        }

        token.setStatus(newStatus);

        if (newStatus == Token.Status.COMPLETED) {
            token.setCompletedAt(LocalDateTime.now());
        }

        return tokenRepository.save(token);
    }

    @Override
    public Token getById(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}