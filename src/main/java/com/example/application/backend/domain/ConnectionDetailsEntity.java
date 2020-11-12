package com.example.application.backend.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "connection_details")
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connection_details_ids_gen")
    @SequenceGenerator(name = "connection_details_ids_gen", sequenceName = "connection_details_id_seq", allocationSize = 1)
    private BigInteger id;

    @Column(nullable = false)
    private String dbInstanceName;

    @Column(nullable = false)
    private String hostname;

    @Column(nullable = false)
    private Integer port;

    @Column(nullable = false)
    private String databaseName;

    @Column
    private String username;

    @Column
    private String password;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;


}
