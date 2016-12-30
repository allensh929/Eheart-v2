package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.EheartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Eheart and its DTO EheartDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EheartMapper {

    EheartDTO eheartToEheartDTO(Eheart eheart);

    List<EheartDTO> eheartsToEheartDTOs(List<Eheart> ehearts);

    Eheart eheartDTOToEheart(EheartDTO eheartDTO);

    List<Eheart> eheartDTOsToEhearts(List<EheartDTO> eheartDTOs);
}
