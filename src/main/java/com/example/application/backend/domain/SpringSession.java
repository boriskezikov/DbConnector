package com.example.application.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity(name = "spring_session")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpringSession {

    @Id
    private String primaryId;
    private String sessionId;
    private BigInteger creationTime;
    private BigInteger lastAccessTime;
    private Integer maxInactiveInterval;
    private BigInteger expiryTime;
    private String principalName;
}
