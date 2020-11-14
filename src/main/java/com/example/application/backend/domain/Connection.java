package com.example.application.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "connections")
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connections_ids_gen")
    @SequenceGenerator(name = "connections_ids_gen", sequenceName = "connections_id_seq", allocationSize = 1)
    private BigInteger id;

    private LocalDateTime openTime;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "connection_details_id", nullable = false)
    private ConnectionDetails connectionDetails;

    private String openedBy;

    private String session;

}
