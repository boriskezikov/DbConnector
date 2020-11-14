package com.example.application.backend.utils;

import java.util.Arrays;
import java.util.List;

public final class SQLConstants {

    public static final List<String> pgCountableDataTypes = Arrays.asList("smallint", "integer", "bigint", "decimal", "numeric", "real", "double precision");

    public static final String SELECT_SCHEMAS_SQL = "SELECT * FROM information_schema.schemata;";

    public static final String SELECT_USER_TABLES_SQL = "select *\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name\n" +
            "where t.table_schema not in ('pg_catalog', 'information_schema')" +
            "   limit ? offset ? ;";

    public static final String SELECT_USER_TABLES_COUNT_ROWS_SQL = "select count(1)\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name\n" +
            "where t.table_schema not in ('pg_catalog', 'information_schema');";

    public static final String SELECT_ALL_TABLES_COUNT_ROWS_SQL = "select count(1)\n" +
            "from information_schema.tables as t\n" +
            "         join pg_stat_user_tables as st on st.schemaname = t.table_schema and st.relname = t.table_name\n" +
            "         join pg_statio_user_tables as sto on sto.schemaname = t.table_schema and sto.relname = t.table_name;";

    public static final String SELECT_ALL_TABLES_SQL = "select * from information_schema.tables as t\n" +
            "          left join pg_stat_user_tables as st on t.table_schema = st.schemaname and t.table_name = st.relname\n" +
            "          left join pg_statio_user_tables as sto on t.table_schema = sto.schemaname and t.table_name = sto.relname\n" +
            "          limit ? offset ?   ;";

    public static final String SELECT_COLS_FOR_TABLE_SQL = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS";

    public static final String SELECT_ALL_RECORDS_SQL = "SELECT * FROM %s ;";

    public static final String SELECT_COLUMNS_SQL = "" +
            "select * from information_schema.table_constraints as cs\n" +
            "         join information_schema.key_column_usage as k on cs.table_name = k.table_name and cs.constraint_catalog = k.constraint_catalog\n" +
            "and cs.constraint_schema = k.constraint_schema " +
            "and cs.constraint_name = k.constraint_name\n" +
            "         join information_schema.columns as col on k.table_name = col.table_name and k.table_schema = col.table_schema and k.column_name = col.column_name\n" +
            "         join information_schema.column_privileges as col_p on k.table_name = col_p.table_name and k.column_name = col_p.column_name\n" +
            "and k.table_schema = col_p.table_schema\n" +
            "         left join information_schema.column_domain_usage as d on k.table_schema = d.table_schema and k.table_name = d.table_name and k.column_name = d.column_name\n" +
            " limit ? offset ? ;";

    public static final String SELECT_ROWS_COUNT_SQL = "" +
            "select count(1)  from information_schema.table_constraints as cs\n" +
            "         join information_schema.key_column_usage as k on cs.table_name = k.table_name and cs.constraint_catalog = k.constraint_catalog\n" +
            "and cs.constraint_schema = k.constraint_schema " +
            "and cs.constraint_name = k.constraint_name\n" +
            "         join information_schema.columns as col on k.table_name = col.table_name and k.table_schema = col.table_schema and k.column_name = col.column_name\n" +
            "         join information_schema.column_privileges as col_p on k.table_name = col_p.table_name and k.column_name = col_p.column_name\n" +
            "and k.table_schema = col_p.table_schema\n" +
            "         left join information_schema.column_domain_usage as d on k.table_schema = d.table_schema and k.table_name = d.table_name and k.column_name = d.column_name ;";

    public static final String SELECT_TABLE_STATISTICS = "select * from (select count(1) as cols from information_schema.columns c where c.table_schema = '%s' and c.table_name = '%s') c\n" +
            "cross join\n" +
            "(select count(1) as rows from %s.%s) r;";

    public static final String SELECT_COLUMN_STATISTICS = "select min(%s), max(%s), avg(%s)  from %s;";

    public static final String IS_COLUMN_NUMERIC_SQL = "select data_type from information_schema.columns where  table_schema = ? and table_name = ? and column_name = ?;";

    private SQLConstants() {
    }
}
