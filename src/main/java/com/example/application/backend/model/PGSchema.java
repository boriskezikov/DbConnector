package com.example.application.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PGSchema {

    private String catalog_name;
    private String schema_name;
    private String schema_owner;
    private String default_character_set_catalog;
    private String default_character_set_schema;
    private String default_character_set_name;
    private String sql_path;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Statistics {
        private Integer tablesCount;
    }


}
