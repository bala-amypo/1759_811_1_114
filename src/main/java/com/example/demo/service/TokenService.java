package com.example.demo.service;

import com.example.demo.entity.Token;
import java.util.List;

public interface TokenService {

    Token issueToken(Long counterId);

    Token updateStatus(Long tokenId, String status);

    Token getToken(Long tokenId);

    Token getTokenByNumber(String tokenNumber); // required for test t45

    List<Token> getWaitingTokens(Long counterId); // required for tests t41, t56, t60
}
