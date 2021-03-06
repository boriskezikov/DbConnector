package com.example.application.backend.repository;

import com.example.application.backend.model.PGSchema;
import com.example.application.backend.model.PGTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.application.backend.utils.SQLConstants.SELECT_SCHEMAS_SQL;

@Slf4j
@Repository(value = "PGSchema")
@RequiredArgsConstructor
public class PGSchemaRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<PGSchema> listSchemas() {
        return jdbcTemplate.query(SELECT_SCHEMAS_SQL, new BeanPropertyRowMapper<>(PGSchema.class));
    }
}
