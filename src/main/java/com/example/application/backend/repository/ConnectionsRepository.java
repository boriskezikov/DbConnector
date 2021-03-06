package com.example.application.backend.repository;

import com.example.application.backend.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionsRepository extends JpaRepository<Connection, BigInteger> {

    boolean existsBySessionAndConnectionDetails_DbInstanceName(String session, String instanceName);

    List<Connection> findAllBySession(String session);

    Optional<Connection> findBySessionAndConnectionDetailsId(String session, BigInteger id);

    void deleteAllBySession(String session);

}
