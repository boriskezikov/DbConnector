package com.example.application.backend.mapper;

import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.service.ConnectionDetailsService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ConnectionDetailsService.class}, componentModel = "spring")
public interface ConnectionDetailsMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    ConnectionDetails createDtoToEntity(CreateConnectionDetailsDTO source);

    CreateConnectionDetailsDTO entityToCreateDto(ConnectionDetails source);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    ConnectionDetails getDtoToEntity(GetConnectionDetailsDTO source);

    GetConnectionDetailsDTO entityToGetDto(ConnectionDetails source);


}
