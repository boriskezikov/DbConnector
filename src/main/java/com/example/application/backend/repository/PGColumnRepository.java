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

import static com.example.application.backend.utils.SQLConstants.IS_COLUMN_NUMERIC_SQL;
import static com.example.application.backend.utils.SQLConstants.SELECT_COLUMNS_SQL;
import static com.example.application.backend.utils.SQLConstants.SELECT_COLUMN_STATISTICS;
import static com.example.application.backend.utils.SQLConstants.SELECT_ROWS_COUNT_SQL;
import static com.example.application.backend.utils.SQLConstants.pgCountableDataTypes;
import static java.lang.String.format;

@SuppressWarnings("ConstantConditions")
@Slf4j
@Repository(value = "PGColumn")
@RequiredArgsConstructor
public class PGColumnRepository {

    private final JdbcTemplate jdbcTemplate;

    public Page<PGColumn> listColumns(Pageable pageable) {
        Integer count = jdbcTemplate.queryForObject(SELECT_ROWS_COUNT_SQL, Integer.class);
        var cols = jdbcTemplate.query(SELECT_COLUMNS_SQL, new Object[]{pageable.getPageSize(), pageable.getOffset()}, new BeanPropertyRowMapper<>(PGColumn.class));
        return new PageImpl<>(cols, pageable, count);
    }

    public PGColumn.Statistics statistics(String table, String schema, String column) {
        String type = jdbcTemplate.queryForObject(IS_COLUMN_NUMERIC_SQL, new Object[]{schema, table, column}, String.class);
        if (pgCountableDataTypes.contains(type)) {
            String sql = format(SELECT_COLUMN_STATISTICS, column, column, column, table);
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(PGColumn.Statistics.class));
        } else {
            throw new RuntimeException(format("Table '%s' has '%s' datatype. Countable calculation can not be performed!", table, type));
        }
    }
}
