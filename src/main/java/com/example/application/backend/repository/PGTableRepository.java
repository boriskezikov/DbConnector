package com.example.application.backend.repository;

import com.example.application.backend.model.PGColumn;
import com.example.application.backend.model.PGTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.application.backend.utils.SQLConstants.SELECT_ALL_TABLES_COUNT_ROWS_SQL;
import static com.example.application.backend.utils.SQLConstants.SELECT_ALL_TABLES_SQL;
import static com.example.application.backend.utils.SQLConstants.SELECT_COLS_FOR_TABLE_SQL;
import static com.example.application.backend.utils.SQLConstants.SELECT_TABLE_STATISTICS;
import static com.example.application.backend.utils.SQLConstants.SELECT_USER_TABLES_COUNT_ROWS_SQL;
import static com.example.application.backend.utils.SQLConstants.SELECT_USER_TABLES_SQL;
import static java.lang.String.format;

@SuppressWarnings("ConstantConditions")
@Slf4j
@Repository(value = "PGTable")
@RequiredArgsConstructor
public class PGTableRepository {

    private final JdbcTemplate jdbcTemplate;

    public Page<PGTable> listTables(boolean onlyUserTables, Pageable pageable) {
        int count;
        List<PGTable> tables;
        if (onlyUserTables) {
            count = jdbcTemplate.queryForObject(SELECT_USER_TABLES_COUNT_ROWS_SQL, Integer.class);
            tables = jdbcTemplate.query(SELECT_USER_TABLES_SQL, new Object[]{pageable.getPageSize(), pageable.getOffset()}, new BeanPropertyRowMapper<>(PGTable.class));
        } else {
            count = jdbcTemplate.queryForObject(SELECT_ALL_TABLES_COUNT_ROWS_SQL, Integer.class);
            tables = jdbcTemplate.query(SELECT_ALL_TABLES_SQL, new Object[]{pageable.getPageSize(), pageable.getOffset()}, new BeanPropertyRowMapper<>(PGTable.class));
        }
        List<PGColumn> res = jdbcTemplate.query(SELECT_COLS_FOR_TABLE_SQL, new BeanPropertyRowMapper<>(PGColumn.class));
        tables.forEach(table -> {
            var cols = res.parallelStream().filter(pgColumn -> pgColumn.getTable_name().equals(table.getTable_name()))
                    .collect(Collectors.toList());
            table.setColumns(cols);
        });
        return new PageImpl<>(tables, pageable, count);
    }

    public PGTable.Statistics statistics(String table, String schema) {
        String sql = format(SELECT_TABLE_STATISTICS, schema, table, schema, table);
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(PGTable.Statistics.class));
    }
}
