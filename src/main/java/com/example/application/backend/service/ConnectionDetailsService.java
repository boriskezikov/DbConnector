package com.example.application.backend.service;

import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.mapper.ConnectionDetailsMapper;
import com.example.application.backend.repository.ConnectionDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionDetailsService {

    private final ConnectionDetailsRepository connectionDetailsRepository;
    private final ConnectionsService connectionsService;
    private final ConnectionDetailsMapper mapper;

    @Transactional
    public BigInteger createConnection(CreateConnectionDetailsDTO connectionDTO) {
        log.info("Try to create new connection to {}:{}", connectionDTO.getHostname(),connectionDTO.getPort());
        var newConnectionDetails  = mapper.createDtoToEntity(connectionDTO);
        var details = connectionDetailsRepository.save(newConnectionDetails);
        var con_id = connectionsService.createConnection(details);
        log.info("Created new connection {}", con_id);
        return con_id;
    }

    public GetConnectionDetailsDTO getConnectionById(BigInteger id) {
        var connection = connectionDetailsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.entityToGetDto(connection);
    }

    public List<GetConnectionDetailsDTO> findAllConnections() {
        var connections = connectionDetailsRepository.findAll();
        return connections.stream().map(mapper::entityToGetDto).collect(toList());
    }
}
