package com.example.application.backend.mapper;

import com.example.application.backend.domain.ConnectionDetailsEntity;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.service.ConnectionDetailsService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {ConnectionDetailsService.class}, componentModel = "spring")
public interface ConnectionDetailsMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    ConnectionDetailsEntity createDtoToEntity(CreateConnectionDetailsDTO source);

    CreateConnectionDetailsDTO entityToCreateDto(ConnectionDetailsEntity source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    ConnectionDetailsEntity getDtoToEntity(GetConnectionDetailsDTO source);

    GetConnectionDetailsDTO entityToGetDto(ConnectionDetailsEntity source);


}
