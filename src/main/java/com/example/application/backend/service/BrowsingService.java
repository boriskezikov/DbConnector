package com.example.application.backend.service;

import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.model.PGColumn;
import com.example.application.backend.model.PGSchema;
import com.example.application.backend.model.PGTable;
import com.example.application.backend.repository.AppSessionRepository;
import com.example.application.backend.repository.ConnectionsRepository;
import com.example.application.backend.repository.PGColumnRepository;
import com.example.application.backend.repository.PGSchemaRepository;
import com.example.application.backend.repository.PGTableRepository;
import com.example.application.backend.repository.PreviewRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
//    private final Map<String, PGRepository<? extends PGObject>> dao;
    private final PGSchemaRepository pgSchemaRepository;
    private final PGTableRepository pgTableRepository;
    private final PGColumnRepository pgColumnRepository;
    private final PreviewRepository previewRepository;


    public List<PGTable> listTables(BigInteger detailsId, boolean onlyUserTables) {
        establishConnection(detailsId);
        return pgTableRepository.search(onlyUserTables);
    }

    public List<PGSchema> listSchemas(BigInteger detailsId){
        establishConnection(detailsId);
        return pgSchemaRepository.search();
    }

    public List<PGColumn> listColumns(BigInteger detailsId){
        establishConnection(detailsId);
        return pgColumnRepository.search();
    }

    public void executeUpdate(String query,BigInteger detailsId) {
        establishConnection(detailsId);
        jdbcTemplate.update(query);
    }

    public List<Map<String, Object>> previewTable(String table, BigInteger detailsId){
        establishConnection(detailsId);
        return previewRepository.search(table);
    }

    private void establishConnection(BigInteger detailsId) {
        appSessionRepository.findBySessionId(session.getId()).ifPresentOrElse(springSession -> {
            Connection connection = connectionsRepository.findBySessionAndConnectionDetailsId(springSession.getSessionId(), detailsId)
                    .orElseThrow(() -> {
                        throw new EntityNotFoundException(format("Instance %s does not belong to current session." +
                                "Please recreate connection details or start a new session.", detailsId));
                    });
            setDataSource(connection.getConnectionDetails());
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
