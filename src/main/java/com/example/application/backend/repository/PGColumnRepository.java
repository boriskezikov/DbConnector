package com.example.application.backend.repository;

import com.example.application.backend.model.PGColumn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository(value = "PGColumn")
@RequiredArgsConstructor
public class PGColumnRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_SCHEMAS_SQL = "select *\n" +
            "from information_schema.table_constraints as cs\n" +
            "         join information_schema.key_column_usage as k on cs.table_name = k.table_name\n" +
            "    and cs.constraint_catalog = k.constraint_catalog\n" +
            "    and cs.constraint_schema = k.constraint_schema\n" +
            "    and cs.constraint_name = k.constraint_name\n" +
            "         join information_schema.columns as col on k.table_name = col.table_name\n" +
            "    and k.table_schema = col.table_schema\n" +
            "    and k.column_name = col.column_name\n" +
            "         join information_schema.column_privileges as col_p\n" +
            "              on k.table_name = col_p.table_name and k.column_name = col_p.column_name and\n" +
            "                 k.table_schema = col_p.table_schema\n" +
            "         left join information_schema.column_domain_usage as d\n" +
            "              on k.table_schema = d.table_schema and k.table_name = d.table_name and k.column_name = d.column_name;";

    public List<PGColumn> listColumns() {
        return jdbcTemplate.query(SELECT_SCHEMAS_SQL, new BeanPropertyRowMapper<>(PGColumn.class));
    }

    public List<PGColumn.Statistics> statistics(String table){
        return null;
    }
}
