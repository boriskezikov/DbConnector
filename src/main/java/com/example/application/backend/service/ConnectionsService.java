package com.example.application.backend.service;

import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.repository.AppSessionRepository;
import com.example.application.backend.repository.ConnectionsRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionsService {

    private final ConnectionsRepository connectionsRepository;
    private final HttpSession session;
    private final JdbcTemplate jdbcTemplate;
    private final AppSessionRepository appSessionRepository;
    private final HttpServletRequest request;

    @Transactional
    public void createConnection(ConnectionDetails connectionDetails) {
        InetAddress netAddr = UtilService.getClientIpAddr(request);
        var connection = Connection.builder()
                .connectionDetails(connectionDetails)
                .open(true)
                .openTime(LocalDateTime.now())
                .openedBy(ofNullable(netAddr)
                        .map((Function<InetAddress, Object>) InetAddress::getHostAddress)
                        .orElse("unknown").toString())
                .session(session.getId())
                .build();
        connectionsRepository.save(connection);
    }

    public void execute(String query, String instanceName) {
        appSessionRepository.findBySessionId(session.getId()).ifPresentOrElse(springSession -> {
            Connection connection = connectionsRepository.findBySessionAndConnectionDetails_DbInstanceName(springSession.getSessionId(), instanceName)
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(format("Instance %s does not belong to current session." +
                                "Please recreate connection details or start a new session.", instanceName));
                    });
            setDataSource(connection.getConnectionDetails());
            jdbcTemplate.update(query);
        }, () -> {
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
