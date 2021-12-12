package com.example.application.backend.service;

import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.model.PGColumn;
import com.example.application.backend.model.PGSchema;
import com.example.application.backend.model.PGTable;
import com.example.application.backend.repository.AppSessionRepository;
import com.example.application.backend.repository.ConnectionsRepository;
import com.example.application.backend.repository.PGColumnRepository;
import com.example.application.backend.repository.PGPreviewRepository;
import com.example.application.backend.repository.PGSchemaRepository;
import com.example.application.backend.repository.PGTableRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrowsingService {

    private final ConnectionsRepository connectionsRepository;
    private final HttpSession session;
    private final JdbcTemplate jdbcTemplate;
    private final AppSessionRepository appSessionRepository;
    private final PGSchemaRepository pgSchemaRepository;
    private final PGTableRepository pgTableRepository;
    private final PGColumnRepository pgColumnRepository;
    private final PGPreviewRepository PGPreviewRepository;

    //test3
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<PGTable> listTables(BigInteger detailsId, boolean onlyUserTables, Pageable pageable) {
        establishConnection(detailsId);
        return pgTableRepository.listTables(onlyUserTables, pageable);
    }

    //test4
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PGSchema> listSchemas(BigInteger detailsId) {
        establishConnection(detailsId);
        return pgSchemaRepository.listSchemas();
    }

    //test3
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Page<PGColumn> listColumns(BigInteger detailsId, Pageable pageable) {
        establishConnection(detailsId);
        return pgColumnRepository.listColumns(pageable);
    }

    //test4
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PGColumn.Statistics listColumnsStat(String column, String table, BigInteger detailsId) {
        var details = establishConnection(detailsId);
        return pgColumnRepository.statistics(table, details.getSchema(), column);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PGTable.Statistics listTablesStat(BigInteger detailsId, String table) {
        var details = establishConnection(detailsId);
        return pgTableRepository.statistics(table, details.getSchema());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Map<String, Object>> previewTable(String table, BigInteger detailsId) {
        establishConnection(detailsId);
        return PGPreviewRepository.search(table);
    }

    private ConnectionDetails establishConnection(BigInteger detailsId) {
        var currentSession = appSessionRepository.findBySessionId(session.getId());
        if (currentSession.isPresent()) {
            Connection connection = connectionsRepository.findBySessionAndConnectionDetailsId(currentSession.get().getSessionId(), detailsId)
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(format("Instance %s does not belong to current session." +
                                "Please recreate connection details or start a new session.", detailsId));
                    });
            var details = connection.getConnectionDetails();
            setDataSource(details);
            return details;
        } else {
            throw new RuntimeException("No info found for current session!");
        }
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
