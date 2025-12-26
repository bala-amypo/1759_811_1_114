package com.example.demo.service;

import com.example.demo.entity.QueuePosition;
import java.util.List;

public interface QueueService {
    QueuePosition updateQueuePosition(Long tokenId, int position);
    QueuePosition getPosition(Long tokenId);

    // NEW method required by controller/testcases
    List<QueuePosition> getQueue();
}
