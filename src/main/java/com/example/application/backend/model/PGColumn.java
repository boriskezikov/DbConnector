package com.example.application.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PGColumn {

    public String is_updatable;
    private String constraint_catalog;
    private String constraint_schema;
    private String constraint_name;
    private String table_catalog;
    private String table_schema;
    private String table_name;
    private String constraint_type;
    private String is_deferrable;
    private String initially_deferred;
    private String enforced;
    private String column_name;
    private Integer ordinal_position;
    private String position_in_unique_constraint;
    private String column_default;
    private String is_nullable;
    private String data_type;
    private String character_maximum_length;
    private String character_octet_length;
    private String numeric_precision;
    private String numeric_precision_radix;
    private String numeric_scale;
    private String datetime_precision;
    private String interval_type;
    private String interval_precision;
    private String character_set_catalog;
    private String character_set_schema;
    private String character_set_name;
    private String collation_catalog;
    private String collation_schema;
    private String collation_name;
    private String domain_catalog;
    private String domain_schema;
    private String domain_name;
    private String udt_catalog;
    private String udt_schema;
    private String udt_name;
    private String scope_catalog;
    private String scope_schema;
    private String scope_name;
    private String maximum_cardinality;
    private String dtd_identifier;
    private String is_self_referencing;
    private String is_identity;
    private String identity_generation;
    private String identity_start;
    private String identity_increment;
    private String identity_maximum;
    private String identity_minimum;
    private String identity_cycle;
    private String is_generated;
    private String generation_expression;
    private String grantor;
    private String grantee;
    private String privilege_type;
    private String is_grantable;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Statistics {
        private Integer min;
        private Integer max;
        private Integer avg;
        private Integer median;
    }

}
