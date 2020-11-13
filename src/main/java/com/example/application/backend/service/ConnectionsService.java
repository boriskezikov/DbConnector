package com.example.application.backend.service;

import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.repository.ConnectionsRepository;
import com.example.application.backend.repository.AppSessionRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionsService {

    private final ConnectionsRepository connectionsRepository;
    private final HttpSession session;
    private final JdbcTemplate jdbcTemplate;
    private final AppSessionRepository appSessionRepository;

    @Transactional
    public BigInteger createConnection(ConnectionDetails connectionDetails) {
        var connection = Connection.builder()
                .connectionDetails(connectionDetails)
                .openTime(LocalDateTime.now())
                .openedBy()
                .session(session.getId())
                .build();
        return connectionsRepository.save(connection).getId();
    }

    public void execute(String query, String instanceName) {
        appSessionRepository.findBySessionId(session.getId()).ifPresentOrElse(springSession -> {
            Connection connection = connectionsRepository.findBySessionAndConnectionDetails_DbInstanceName(springSession.getSessionId(), instanceName);
            setDataSource(connection.getConnectionDetails());
            jdbcTemplate.update("INSERT INTO TEST_TABLE (field, id) VALUES ('test', 1);");
        }, ()-> {
            log.error("No info found for current session!");
        });
    }

    private void setDataSource(ConnectionDetails cd) {
        var url = format("jdbc:postgresql://%s:%s/%s?currentSchema=%s", cd.getHostname(), cd.getPort(), cd.getDatabaseName(), cd.getSchema());
        var cfg = new HikariConfig();
        cfg.setUsername(cd.getUsername());
        cfg.setPassword((cd.getPassword()));
        cfg.setJdbcUrl(url);
        cfg.setDriverClassName("org.postgresql.Driver");
        jdbcTemplate.setDataSource(new HikariDataSource(cfg));
    }
}
