package com.example.application.backend.repository;

import com.example.application.backend.domain.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ConnectionsRepository extends JpaRepository<Connection, BigInteger> {
}
