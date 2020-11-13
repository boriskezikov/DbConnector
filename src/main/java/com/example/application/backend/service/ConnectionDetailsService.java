package com.example.application.backend.service;

import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.mapper.ConnectionDetailsMapper;
import com.example.application.backend.repository.ConnectionDetailsRepository;
import com.example.application.backend.repository.ConnectionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionDetailsService {

    private final ConnectionDetailsRepository connectionDetailsRepository;
    private final ConnectionsRepository connectionsRepository;
    private final ConnectionsService connectionsService;
    private final ConnectionDetailsMapper mapper;
    private final HttpSession httpSession;

    @Transactional
    public void createConnectionDetails(CreateConnectionDetailsDTO detailsDTO) {
        log.info("Try to create new connection to {}:{}", detailsDTO.getHostname(), detailsDTO.getPort());
        var newConnectionDetails = mapper.createDtoToEntity(detailsDTO);
        validateDetails(newConnectionDetails);
        var details = connectionDetailsRepository.save(newConnectionDetails);
        connectionsService.createConnection(details);
        log.info("New connection to: {}:{} with name:{} established for session: {}",detailsDTO.getHostname(),detailsDTO.getPort(), detailsDTO.getDbInstanceName(), httpSession.getId());
    }

    public GetConnectionDetailsDTO getConnectionDetailsById(BigInteger id) {
        var details = connectionDetailsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.entityToGetDto(details);
    }

    public List<GetConnectionDetailsDTO> findAllConnections() {
        var connections = connectionDetailsRepository.findAll();
        return connections.stream().map(mapper::entityToGetDto).collect(toList());
    }

    private void validateDetails(ConnectionDetails cd) {
        if (connectionsRepository.existsBySessionAndConnectionDetails_DbInstanceName(httpSession.getId(), cd.getDbInstanceName())) {
            throw new EntityExistsException(format("Db instance %s already exists for current session. Please specify another name", cd.getDbInstanceName()));
        }
    }
}
