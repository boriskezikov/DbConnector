package com.example.application.backend.service;

import com.example.application.backend.domain.DbConnectionEntity;
import com.example.application.backend.dto.DbConnectionDTO;
import com.example.application.backend.mapper.DbConnectionMapper;
import com.example.application.backend.repository.DbConnectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbConnectionService {

    private final DbConnectionRepository dbConnectionRepository;
    private final DbConnectionMapper mapper;


    public DbConnectionEntity createConnection(DbConnectionDTO connectionDTO) {
        return dbConnectionRepository.save(mapper.dtoToEntity(connectionDTO));
    }

    public DbConnectionEntity getConnectionById(BigInteger id) {
        return dbConnectionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<DbConnectionEntity> findAll() {
        return dbConnectionRepository.findAll();
    }
}
