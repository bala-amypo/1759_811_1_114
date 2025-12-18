package com.example.demo.service;

import com.example.demo.entity.TokenEntity;

public interface TokenService {

    TokenEntity issueToken(Long counterId);

    TokenEntity updateStatus(Long tokenId, String status);

    TokenEntity getToken(Long tokenId);
}