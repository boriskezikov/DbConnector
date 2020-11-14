package com.example.application.backend.controller;


import com.example.application.backend.model.PGColumn;
import com.example.application.backend.model.PGSchema;
import com.example.application.backend.model.PGTable;
import com.example.application.backend.service.BrowsingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful request"),
        @ApiResponse(responseCode = "500", description = "Internal error")
})
@Tag(name = "InfoBrowsingController", description = "Access to structure and data for db connections")
@RestController
@RequestMapping("api/browse")
@RequiredArgsConstructor
public class InfoBrowsingController {

    private final BrowsingService browsingService;

    @Operation(summary = "List existing schemas for current session and connection")
    @Parameter(name = "detailsId", description = "Connection details ID")
    @GetMapping("/schemas")
    @ResponseStatus(HttpStatus.OK)
    public List<PGSchema> loadAvailableSchemas(@RequestParam BigInteger detailsId) {
        return browsingService.listSchemas(detailsId);
    }

    @Operation(summary = "List existing tables for current session and connection")
    @Parameter(name = "onlyUserTables", description = "If true - returns only user created tables, else returns all")
    @Parameter(name = "detailsId", description = "Connection details ID")
    @GetMapping("/tables")
    @ResponseStatus(HttpStatus.OK)
    public Page<PGTable> loadAvailableTables(Pageable pageable,
                                             @RequestParam(defaultValue = "true") boolean onlyUserTables,
                                             @RequestParam BigInteger detailsId) {
        return browsingService.listTables(detailsId, onlyUserTables, pageable);
    }

    @Operation(summary = "Stat for tables")
    @Parameter(name = "detailsId", description = "Connection details ID")
    @GetMapping("/tables/stat")
    @ResponseStatus(HttpStatus.OK)
    public PGTable.Statistics listTablesStat(@RequestParam BigInteger detailsId, @RequestParam String table) {
        return browsingService.listTablesStat(detailsId, table);
    }

    @Operation(summary = "List existing columns for current session and connection")
    @Parameter(name = "detailsId", description = "Connection details ID")
    @GetMapping("/columns")
    @ResponseStatus(HttpStatus.OK)
    public Page<PGColumn> loadAvailableColumns(Pageable pageable, @RequestParam BigInteger detailsId) {
        return browsingService.listColumns(detailsId, pageable);
    }

    @Operation(summary = "Stat for columns")
    @Parameter(name = "detailsId", description = "Connection details ID")
    @Parameter(name = "tableName", description = "Table name for fetching statistics")
    @Parameter(name = "columnName", description = "Column name for fetching statistics")
    @GetMapping("/columns/stat")
    @ResponseStatus(HttpStatus.OK)
    public PGColumn.Statistics listColumnsStat(@RequestParam String tableName, @RequestParam String columnName, @RequestParam BigInteger detailsId) {
        return browsingService.listColumnsStat(columnName, tableName, detailsId);
    }

    @Operation(summary = "Preview for data stored in specified table")
    @GetMapping("/preview")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> previewTable(@RequestParam String tableName, @RequestParam BigInteger detailsId) {
        return browsingService.previewTable(tableName, detailsId);
    }

}
