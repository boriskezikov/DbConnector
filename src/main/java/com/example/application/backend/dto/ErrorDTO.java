package com.example.application.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Error")
public class ErrorDTO {

    @Schema(description = "HTTP status")
    private Integer status;

    @Schema(description = "Error type")
    private String errorType;

    @Schema(description = "Error message")
    private String message;

}
