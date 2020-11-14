package com.example.application.backend.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.example.application.backend.utils.SQLConstants.SELECT_ALL_RECORDS_SQL;
import static java.lang.String.format;

@Slf4j
@Repository(value = "PGPreview")
@RequiredArgsConstructor
public class PGPreviewRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Map<String, Object>> search(String table) {
        return jdbcTemplate.queryForList(format(SELECT_ALL_RECORDS_SQL, table));
    }

}
