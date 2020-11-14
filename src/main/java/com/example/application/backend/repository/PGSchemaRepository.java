package com.example.application.backend.repository;

import com.example.application.backend.model.PGSchema;
import com.example.application.backend.model.PGTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository(value = "PGSchema")
@RequiredArgsConstructor
public class PGSchemaRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_SCHEMAS_SQL = "SELECT * FROM information_schema.schemata;";

    public List<PGSchema> listSchemas() {
        return jdbcTemplate.query(SELECT_SCHEMAS_SQL, new BeanPropertyRowMapper<>(PGSchema.class));
    }

    public List<PGSchema.Statistics> statistics() {
        return null;
    }
}
