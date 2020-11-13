package com.example.application.backend.service;

import com.example.application.backend.adapter.DbSessionAdapter;
import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.repository.ConnectionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionsService implements Serializable {

    private static final long serialVersionUID = 1770344678739003989L;
    private final ConnectionsRepository connectionsRepository;
    private final HttpServletRequest request;
    private final DbSessionAdapter dbSessionAdapter;
    private final HttpSession session;

    @Transactional
    public BigInteger createConnection(ConnectionDetails connectionDetails) {
        var connection = Connection.builder()
                .connectionDetails(connectionDetails)
                .session(session.getId())
                .build();
        return connectionsRepository.save(connection).getId();
    }

    @Transactional
    public ConnectionsService openConnection(BigInteger connectionId) {
        connectionsRepository.findById(connectionId).ifPresent(connection -> {
            var netAddr = UtilService.getClientIpAddr(request);
            connection.setOpenedBy(netAddr != null ? netAddr.getCanonicalHostName() : "unknown");
            connection.setOpen(true);
            connection.setOpenTime(LocalDateTime.now());
            dbSessionAdapter.init(connection.getConnectionDetails());
            connectionsRepository.save(connection);
            session.setAttribute("CONNECTION_ID", connection.getId());
        });
        log.info("Connection established for session {}", session.getId());
        return this;
    }

    @Transactional
    public void destroyConnection() {
        connectionsRepository.findById((BigInteger) session.getAttribute("CONNECTION_ID"))
                .ifPresent(connection -> {
                    connection.setOpen(false);
                    connectionsRepository.save(connection);
                });
    }

    public void test(String query) {
        dbSessionAdapter.getJdbcTemplate().execute(query);
    }
}
