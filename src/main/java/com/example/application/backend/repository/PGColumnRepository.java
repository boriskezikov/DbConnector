package com.example.application.backend.repository;

import com.example.application.backend.model.PGColumn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository(value = "PGColumn")
@RequiredArgsConstructor
public class PGColumnRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_SCHEMAS_SQL = "" +
            "select * from information_schema.table_constraints as cs\n" +
            "         join information_schema.key_column_usage as k on cs.table_name = k.table_name and cs.constraint_catalog = k.constraint_catalog\n" +
            "and cs.constraint_schema = k.constraint_schema " +
            "and cs.constraint_name = k.constraint_name\n" +
            "         join information_schema.columns as col on k.table_name = col.table_name and k.table_schema = col.table_schema and k.column_name = col.column_name\n" +
            "         join information_schema.column_privileges as col_p on k.table_name = col_p.table_name and k.column_name = col_p.column_name\n" +
            "and k.table_schema = col_p.table_schema\n" +
            "         left join information_schema.column_domain_usage as d on k.table_schema = d.table_schema and k.table_name = d.table_name and k.column_name = d.column_name\n" +
            " limit ? offset ? ;";

    private static final String SELECT_ROWS_COUNT_SQL = "" +
            "select count(1)  from information_schema.table_constraints as cs\n" +
            "         join information_schema.key_column_usage as k on cs.table_name = k.table_name and cs.constraint_catalog = k.constraint_catalog\n" +
            "and cs.constraint_schema = k.constraint_schema " +
            "and cs.constraint_name = k.constraint_name\n" +
            "         join information_schema.columns as col on k.table_name = col.table_name and k.table_schema = col.table_schema and k.column_name = col.column_name\n" +
            "         join information_schema.column_privileges as col_p on k.table_name = col_p.table_name and k.column_name = col_p.column_name\n" +
            "and k.table_schema = col_p.table_schema\n" +
            "         left join information_schema.column_domain_usage as d on k.table_schema = d.table_schema and k.table_name = d.table_name and k.column_name = d.column_name ;";

    public Page<PGColumn> listColumns(Pageable pageable) {
        int count = jdbcTemplate.queryForObject(SELECT_ROWS_COUNT_SQL, Integer.class);
        var cols = jdbcTemplate.query(SELECT_SCHEMAS_SQL, new Object[]{pageable.getPageSize(), pageable.getOffset()}, new BeanPropertyRowMapper<>(PGColumn.class));
        return new PageImpl<>(cols, pageable, count);
    }

    public List<PGColumn.Statistics> statistics(String table) {
        return null;
    }
}
