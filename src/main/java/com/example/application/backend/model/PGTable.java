package com.example.application.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PGTable {

    private List<PGColumn> columns;
    private String table_catalog;
    private String table_schema;
    private String table_name;
    private String table_type;
    private String self_referencing_column_name;
    private String reference_generation;
    private String user_defined_type_catalog;
    private String user_defined_type_schema;
    private String user_defined_type_name;
    private String is_insertable_into;
    private String is_typed;
    private String commit_action;
    private Integer relid;
    private String schemaname;
    private String relname;
    private Integer seq_scan;
    private Integer seq_tup_read;
    private Integer idx_scan;
    private Integer idx_tup_fetch;
    private Integer n_tup_ins;
    private Integer n_tup_upd;
    private Integer n_tup_del;
    private Integer n_tup_hot_upd;
    private Integer n_live_tup;
    private Integer n_dead_tup;
    private Integer n_mod_since_analyze;
    private String last_vacuum;
    private String last_autovacuum;
    private String last_analyze;
    private String last_autoanalyze;
    private Integer vacuum_count;
    private Integer autovacuum_count;
    private Integer analyze_count;
    private Integer autoanalyze_count;
    private Integer heap_blks_read;
    private Integer heap_blks_hit;
    private Integer idx_blks_read;
    private Integer idx_blks_hit;
    private Integer toast_blks_read;
    private Integer toast_blks_hit;
    private Integer tidx_blks_read;
    private Integer tidx_blks_hit;
    private String tablename;
    private String tableowner;
    private String tablespace;
    private Boolean hasindexes;
    private Boolean hasrules;
    private Boolean hastriggers;
    private Boolean rowsecurity;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Statistics {
        private Integer recordsCount;
        private Integer attributesCount;
    }


}
