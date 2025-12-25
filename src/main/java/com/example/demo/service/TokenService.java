package com.example.demo.service;

import com.example.demo.entity.Token;
import java.util.List;

public interface TokenService {

    Token issueToken(Long counterId) throws Exception;

    Token updateTokenStatus(Long tokenId, Token.Status newStatus) throws Exception;

    Token getTokenById(Long tokenId) throws Exception;

    List<Token> getTokensByCounterAndStatus(Long counterId, Token.Status status);
}
