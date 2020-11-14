package com.example.application.backend.controller;

import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.dto.UpdateConnectionDetailsDTO;
import com.example.application.backend.service.ConnectionDetailsService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;


@OpenAPIDefinition(info = @Info(title = "Custom database connector",
        description = "Service for managing database connections",
        version = "${application.api.version}",
        contact = @Contact(name = "Kezikov Boris", email = "boris200898@icloud.com")))
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful request"),
        @ApiResponse(responseCode = "201", description = "Connection created"),
        @ApiResponse(responseCode = "500", description = "Internal error")
})
@Tag(name = "ConnectionDetailsController", description = "Access to connection details management")
@RestController
@RequestMapping("/api/connection/details")
@RequiredArgsConstructor
public class ConnectionDetailsController {

    private final ConnectionDetailsService connectionDetailsService;

    @Operation(summary = "Create new connection details instance.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetConnectionDetailsDTO createConnectionDetails(@RequestBody @Validated CreateConnectionDetailsDTO detailsDTO) {
        return connectionDetailsService.createConnectionDetails(detailsDTO);
    }

    @Operation(summary = "Get connection details by id")
    @GetMapping("/{id}")
    public GetConnectionDetailsDTO findById(@PathVariable("id") BigInteger id) {
        return connectionDetailsService.getConnectionDetailsById(id);
    }

    @Operation(summary = "Get all connection details")
    @Parameter(name = "adminMode", description = "If true allows to select all connection details regardless of session," +
            "if false selects details for current session only")
    @GetMapping
    public List<GetConnectionDetailsDTO> findAll(@RequestParam(defaultValue = "false") boolean adminMode) {
        return connectionDetailsService.findAllConnections(adminMode);
    }

    @Operation(summary = "Update existing connection details")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetConnectionDetailsDTO updateConnectionDetails(@RequestBody @Validated UpdateConnectionDetailsDTO detailsDTO) {
        return connectionDetailsService.update(detailsDTO);
    }

    @Operation(summary = "Manual closing of the current session")
    @DeleteMapping("/close")
    public void removeConnectionDetails() {
        connectionDetailsService.invalidate();
    }
}
