package com.example.application.backend.service;

import com.example.application.backend.repository.AppSessionRepository;
import com.example.application.backend.repository.ConnectionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundOperationsService {

    private final ConnectionsRepository connectionsRepository;
    private final AppSessionRepository sessionRepository;

    //test2
    @Scheduled(fixedDelay = 15000)
    public void clearAllForExpiredSessions() {
        log.info("Starting background storage cleaning operation");
        connectionsRepository.findAll().forEach(connection -> {
            if (!sessionRepository.existsBySessionId(connection.getSession())) {
                connectionsRepository.delete(connection);
                log.warn("Connection {} and details for expired session {} erased", connection.getId(), connection.getSession());
            }
        });
        log.info("Background cleaning operation finished");
    }
}
