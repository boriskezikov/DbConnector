package com.example.application.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbConnectionDTO {

    private BigInteger id;
    private String dbInstanceName;
    private String hostname;
    private Integer port;
    private String databaseName;
    private String username;
    private String password;

}
