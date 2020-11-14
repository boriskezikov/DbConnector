package com.example.application.backend.repository;

import com.example.application.backend.model.PGTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository(value = "PGTable")
@RequiredArgsConstructor
public class PGTableRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_USER_TABLES_SQL = "select *\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name\n" +
            "         join pg_tables pt on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "where table_schema not in ('pg_catalog', 'information_schema')\n" +
            "order by table_schema, table_name;";

    private static final String SELECT_ALL_TABLES_SQL = "select *\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name\n" +
            "         join pg_tables pt on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "order by table_schema, table_name;";

    public List<PGTable> search(boolean onlyUserTables) {
        return jdbcTemplate.query(
                onlyUserTables ? SELECT_USER_TABLES_SQL : SELECT_ALL_TABLES_SQL, new BeanPropertyRowMapper<>(PGTable.class));
    }

}
