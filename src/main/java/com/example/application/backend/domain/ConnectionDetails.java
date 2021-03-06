package com.example.application.backend.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.time.LocalDateTime;


@Data
@Entity(name = "connection_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connection_details_ids_gen")
    @SequenceGenerator(name = "connection_details_ids_gen", sequenceName = "connection_details_id_seq", allocationSize = 1)
    private BigInteger id;

    private String dbInstanceName;

    private String hostname;

    private Integer port;

    private String databaseName;

    private String schema;

    private String username;

    private String password;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

}
