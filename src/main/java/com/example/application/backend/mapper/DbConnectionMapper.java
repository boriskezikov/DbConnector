package com.example.application.backend.mapper;

import com.example.application.backend.domain.DbConnectionEntity;
import com.example.application.backend.dto.DbConnectionDTO;
import com.example.application.backend.service.DbConnectionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {DbConnectionService.class}, componentModel = "spring")
public interface DbConnectionMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    DbConnectionEntity dtoToEntity(DbConnectionDTO source);

    DbConnectionDTO entityToDto(DbConnectionEntity source);

    List<DbConnectionDTO> entityToDto(List<DbConnectionEntity> source);

}
