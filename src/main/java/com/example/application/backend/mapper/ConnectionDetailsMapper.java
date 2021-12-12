package com.example.application.backend.mapper;

import com.example.application.backend.domain.ConnectionDetails;
import com.example.application.backend.dto.CreateConnectionDetailsDTO;
import com.example.application.backend.dto.GetConnectionDetailsDTO;
import com.example.application.backend.dto.UpdateConnectionDetailsDTO;
import com.example.application.backend.service.ConnectionDetailsService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

//mapper
@Mapper(uses = {ConnectionDetailsService.class}, componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConnectionDetailsMapper {

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    ConnectionDetails createDtoToEntity(CreateConnectionDetailsDTO source);

    GetConnectionDetailsDTO entityToGetDto(ConnectionDetails source);

    //test2
    void updateEntityFromDto(UpdateConnectionDetailsDTO source, @MappingTarget ConnectionDetails target);

}
