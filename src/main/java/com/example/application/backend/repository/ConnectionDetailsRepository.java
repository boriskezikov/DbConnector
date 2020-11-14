package com.example.application.backend.repository;

import com.example.application.backend.domain.ConnectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ConnectionDetailsRepository extends JpaRepository<ConnectionDetails, BigInteger> {
}
