package com.example.demo.service;

import com.example.demo.entity.TokenLog;
import java.util.List;

public interface TokenLogService {

    TokenLog createLog(Long tokenId, String message) throws Exception;

    List<TokenLog> getLogs(Long tokenId) throws Exception;
}
