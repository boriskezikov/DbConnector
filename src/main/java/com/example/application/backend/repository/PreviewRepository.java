package com.example.application.backend.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Repository(value = "PGPreview")
@RequiredArgsConstructor
public class PreviewRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_SCHEMAS_SQL = "SELECT * FROM %s ;";

    public List<Map<String, Object>> search(String table) {
        return jdbcTemplate.queryForList(format(SELECT_SCHEMAS_SQL, table));
    }

}
