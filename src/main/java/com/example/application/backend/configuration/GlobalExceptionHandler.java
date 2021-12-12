package com.example.application.backend.configuration;


import com.example.application.backend.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiErrorDTO handleApiException(Exception ex) {
        return new ApiErrorDTO(500, ex.getClass().getName(), ex.getMessage());
    }

    //test-01kasfkda
    @ResponseBody
    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiErrorDTO handleApiException(EntityNotFoundException ex) {
        return new ApiErrorDTO(404, ex.getClass().getName(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = {IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiErrorDTO handleApiException(IOException ex) {
        return new ApiErrorDTO(400, ex.getClass().getName(), ex.getMessage());
    }

}
