package com.example.application.backend.service;

import com.example.application.backend.domain.ConnectionDetailsEntity;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.mapper.ConnectionDetailsMapper;
import com.example.application.backend.repository.DbConnectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionDetailsService {

    private final DbConnectionRepository dbConnectionRepository;
    private final ConnectionDetailsMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;


    public ConnectionDetailsEntity createConnection(CreateConnectionDetailsDTO connectionDTO) {
        log.info("Try to create new connection to {}:{}", connectionDTO.getHostname(),connectionDTO.getPort());
        var encodedPass = passwordEncoder.encode(connectionDTO.getPassword());
        var newConnection  = mapper.createDtoToEntity(connectionDTO);
        newConnection.setPassword(encodedPass);
        return dbConnectionRepository.save(newConnection);
    }

    public GetConnectionDetailsDTO getConnectionById(BigInteger id) {
        var connection = dbConnectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.entityToGetDto(connection);
    }

    public List<GetConnectionDetailsDTO> findAllConnections() {
        var connections = dbConnectionRepository.findAll();
        return connections.stream().map(mapper::entityToGetDto).collect(toList());
    }
}
