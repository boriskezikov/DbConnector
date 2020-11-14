package com.example.application.backend.service;

import com.example.application.backend.domain.Connection;
import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.dto.UpdateConnectionDetailsDTO;
import com.example.application.backend.mapper.ConnectionDetailsMapper;
import com.example.application.backend.repository.AppSessionRepository;
import com.example.application.backend.repository.ConnectionDetailsRepository;
import com.example.application.backend.repository.ConnectionsRepository;
import com.example.application.backend.utils.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionDetailsService {

    private final ConnectionDetailsRepository connectionDetailsRepository;
    private final ConnectionsRepository connectionsRepository;
    private final AppSessionRepository sessionRepository;
    private final HttpServletRequest request;
    private final ConnectionDetailsMapper mapper;
    private final HttpSession httpSession;

    @Transactional
    public GetConnectionDetailsDTO createConnectionDetails(CreateConnectionDetailsDTO detailsDTO) {
        log.info("Try to create new connection to {}:{}", detailsDTO.getHostname(), detailsDTO.getPort());
        var newConnectionDetails = mapper.createDtoToEntity(detailsDTO);
        validateDetails(newConnectionDetails);
        var details = connectionDetailsRepository.save(newConnectionDetails);
        createConnection(details);
        log.info("New connection details for host {}:{} created for session: {}", detailsDTO.getHostname(), detailsDTO.getPort(), httpSession.getId());
        return mapper.entityToGetDto(details);
    }

    public GetConnectionDetailsDTO getConnectionDetailsById(BigInteger id) {
        var details = connectionDetailsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.entityToGetDto(details);
    }

    public List<GetConnectionDetailsDTO> findAllConnections(boolean adminMode) {
        if (adminMode) {
            return connectionDetailsRepository.findAll().stream()
                    .map(mapper::entityToGetDto).collect(toList());
        } else {
            return connectionsRepository.findAllBySession(httpSession.getId()).stream()
                    .map(Connection::getConnectionDetails)
                    .map(mapper::entityToGetDto).collect(toList());
        }
    }

    public GetConnectionDetailsDTO update(UpdateConnectionDetailsDTO cdDto) {
        var cd = connectionsRepository.findBySessionAndConnectionDetailsId(httpSession.getId(), cdDto.getId())
                .map(Connection::getConnectionDetails)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("ConnectionDetailsID incorrect or does not belong to the current session");
                });
        mapper.updateEntityFromDto(cdDto, cd);
        return mapper.entityToGetDto(connectionDetailsRepository.save(cd));
    }

    @Transactional
    public void invalidate() {
        connectionsRepository.deleteAllBySession(httpSession.getId());
        log.warn("Session {} finished. All connections erased ", httpSession.getId());
        httpSession.invalidate();
    }


    @Transactional
    public void createConnection(ConnectionDetails connectionDetails) {
        InetAddress netAddr = UtilService.getClientIpAddr(request);
        var connection = Connection.builder()
                .connectionDetails(connectionDetails)
                .openTime(LocalDateTime.now())
                .openedBy(ofNullable(netAddr)
                        .map((Function<InetAddress, Object>) InetAddress::getHostAddress)
                        .orElse("unknown").toString())
                .session(httpSession.getId())
                .build();
        connectionsRepository.save(connection);
    }

    private void validateDetails(ConnectionDetails cd) {
        if (connectionsRepository.existsBySessionAndConnectionDetails_DbInstanceName(httpSession.getId(), cd.getDbInstanceName())) {
            throw new EntityExistsException(format("Db instance %s already exists for current session. Please specify another name", cd.getDbInstanceName()));
        }
    }
}
