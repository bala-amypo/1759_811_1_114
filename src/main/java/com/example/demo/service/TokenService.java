package com.example.demo.service;

import com.example.demo.model.Token;

public interface TokenService {

    Token issueToken(Long counterId);

    Token updateToken(Long tokenId, String status);

    Token getToken(Long tokenId);
}
