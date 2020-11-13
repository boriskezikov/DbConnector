package com.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


@SpringBootApplication
@EnableJdbcHttpSession
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
