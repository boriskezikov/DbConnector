package com.example.application.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "connections")
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connections_ids_gen")
    @SequenceGenerator(name = "connections_ids_gen", sequenceName = "connections_id_seq", allocationSize = 1)
    private BigInteger id;

}
