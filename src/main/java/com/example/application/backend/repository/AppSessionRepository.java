package com.example.application.backend.repository;

import com.example.application.backend.domain.SpringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppSessionRepository extends JpaRepository<SpringSession, String> {

    Optional<SpringSession> findBySessionId(String sessionId);
    boolean existsBySessionId(String sessionId);
}
