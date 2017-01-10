package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.DomainDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Domain and its DTO DomainDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DomainMapper {

    DomainDTO domainToDomainDTO(Domain domain);

    List<DomainDTO> domainsToDomainDTOs(List<Domain> domains);

    @Mapping(target = "doctors", ignore = true)
    Domain domainDTOToDomain(DomainDTO domainDTO);

    List<Domain> domainDTOsToDomains(List<DomainDTO> domainDTOs);
}
