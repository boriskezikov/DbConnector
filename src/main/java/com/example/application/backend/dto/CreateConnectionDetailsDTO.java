package com.example.application.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionDetailsDTO {

    @NonNull
    private String dbInstanceName;
    @NonNull
    private String hostname;
    @NonNull
    private Integer port;
    @NonNull
    private String databaseName;
    private String username;
    private String password;
    private String schema;

}
