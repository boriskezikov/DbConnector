package com.example.application.backend.adapter;

import com.example.application.backend.domain.ConnectionDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.io.Serializable;

import static java.lang.String.format;

@Getter
@Slf4j
@Component
@RequiredArgsConstructor
@Scope(value= WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DbSessionAdapter implements Serializable {

    private static final long serialVersionUID = -3978307001636222548L;
    private final JdbcTemplateCustom jdbcTemplate;

    public void init(ConnectionDetails cd) {
        var url = format("jdbc:postgresql://%s:%s/%s?currentSchema=%s", cd.getHostname(), cd.getPort(), cd.getDatabaseName(), cd.getSchema());
        var cfg = new HikariConfig();
        cfg.setUsername(cd.getUsername());
        cfg.setPassword((cd.getPassword()));
        cfg.setJdbcUrl(url);
        cfg.setDriverClassName("org.postgresql.Driver");
        jdbcTemplate.setDataSource(new HikariDataSource(cfg));
    }


//    public T execute(String sql, T responseType) {
//        return jdbcTemplate.query(sql, (ResultSetExtractor<T>) rs -> null);
//    }


}
