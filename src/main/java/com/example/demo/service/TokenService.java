package com.example.demo.service;

import com.example.demo.entity.Token;
import java.util.List;

public interface TokenService {

    Token issueToken(Long counterId);

    Token updateStatus(Long tokenId, String status);

    Token getToken(Long tokenId);

    List<Token> getAllTokens();
}
