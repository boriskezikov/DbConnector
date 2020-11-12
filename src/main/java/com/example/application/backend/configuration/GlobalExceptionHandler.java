package com.example.application.backend.configuration;


import com.example.application.backend.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorDTO handleApiException(Exception ex) {
        return new ErrorDTO(500, ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorDTO handleApiException(EntityNotFoundException ex) {
        return new ErrorDTO(404, ex.getMessage());
    }

}
