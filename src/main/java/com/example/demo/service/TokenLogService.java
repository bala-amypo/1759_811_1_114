package com.example.demo.service;

import com.example.demo.entity.TokenLog;
import java.util.List;

public interface TokenLogService {

    List<TokenLog> getLogsByToken(Long tokenId);
}
