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

@Slf4j
@Repository(value = "PGTable")
@RequiredArgsConstructor
public class PGTableRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_USER_TABLES_SQL = "select *\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name\n" +
            "where t.table_schema not in ('pg_catalog', 'information_schema')" +
            "limit ? offset ?;";

    private static final String SELECT_USER_TABLES_COUNT_ROWS ="select count(1)\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name\n" +
            "where t.table_schema not in ('pg_catalog', 'information_schema');";

    private static final String SELECT_ALL_TABLES_COUNT_ROWS ="select count(1)\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name;";

    private static final String SELECT_ALL_TABLES_SQL = "select * from information_schema.tables as t\n" +
            "          left join pg_stat_user_tables as st on t.table_schema = st.schemaname and t.table_name = st.relname\n" +
            "          left join pg_statio_user_tables as sto on t.table_schema = sto.schemaname and t.table_name = sto.relname" +
            "          limit ? offset ?   ;";

    private static final String SELECT_COLS_FOR_TABLE_SQL = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS";

    public Page<PGTable> listTables(boolean onlyUserTables, Pageable pageable) {
        int count;
        List<PGTable> tables;
        if (onlyUserTables) {
            count = jdbcTemplate.queryForObject(SELECT_USER_TABLES_COUNT_ROWS, (rs, rowNum) -> rs.getInt(1));
            tables = jdbcTemplate.query(SELECT_USER_TABLES_SQL, new Object[]{pageable.getPageSize(), pageable.getOffset()},new BeanPropertyRowMapper<>(PGTable.class));
        }
        else {
            count = jdbcTemplate.queryForObject(SELECT_ALL_TABLES_COUNT_ROWS, (rs, rowNum) -> rs.getInt(1));
            tables = jdbcTemplate.query(SELECT_ALL_TABLES_SQL, new Object[]{pageable.getPageSize(), pageable.getOffset()},new BeanPropertyRowMapper<>(PGTable.class));
        }
        List<PGColumn> res = jdbcTemplate.query(SELECT_COLS_FOR_TABLE_SQL, new BeanPropertyRowMapper<>(PGColumn.class));
        tables.forEach(table -> {
            var cols = res.parallelStream().filter(pgColumn -> pgColumn.getTable_name().equals(table.getTable_name()))
                    .collect(Collectors.toList());
            table.setColumns(cols);
        });
        return new PageImpl<>(tables, pageable, count);
    }

    public List<PGTable.Statistics> statistics() {
        return null;
    }


}
