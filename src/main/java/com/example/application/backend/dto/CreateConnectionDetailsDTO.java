package com.example.application.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionDetailsDTO {

    private String dbInstanceName;
    private String hostname;
    private Integer port;
    private String databaseName;
    private String username;
    private String password;

}
