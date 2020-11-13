package com.example.application.backend.adapter;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.Serializable;

@Component
@Scope(value= WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JdbcTemplateCustom extends JdbcTemplate implements Serializable {

    public JdbcTemplateCustom() {
    }

    public JdbcTemplateCustom(DataSource dataSource) {
        super(dataSource);
    }

    public JdbcTemplateCustom(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    private static final long serialVersionUID = -3455834063024600453L;
}
