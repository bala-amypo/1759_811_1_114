package com.example.demo.service.impl;

import com.example.demo.entity.Queue;
import com.example.demo.entity.Token;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QueueRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final TokenRepository tokenRepository;

  
    public QueueServiceImpl(QueueRepository queueRepository, TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Queue updatePosition(Token token, Integer newPosition) {
        if (token == null) {
            throw new ResourceNotFoundException("Token not found");
        }

        Queue queue = queueRepository.findByTokenId(token.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Queue entry not found for token"));

        queue.setPosition(newPosition);
        return queueRepository.save(queue);
    }

    @Override
    public Queue getByTokenId(Long tokenId) {
        return queueRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue entry not found for token"));
    }
}
