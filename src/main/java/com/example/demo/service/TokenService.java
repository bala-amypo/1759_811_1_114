package com.example.demo.service;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.User;

public interface TokenService {

    Token issueToken(User user, ServiceCounter counter);

    Token updateStatus(Long tokenId, Token.Status newStatus);

    Token getById(Long tokenId);
}
