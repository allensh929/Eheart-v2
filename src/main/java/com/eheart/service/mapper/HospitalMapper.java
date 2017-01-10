package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.HospitalDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Hospital and its DTO HospitalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HospitalMapper {

    HospitalDTO hospitalToHospitalDTO(Hospital hospital);

    List<HospitalDTO> hospitalsToHospitalDTOs(List<Hospital> hospitals);

    @Mapping(target = "departments", ignore = true)
    Hospital hospitalDTOToHospital(HospitalDTO hospitalDTO);

    List<Hospital> hospitalDTOsToHospitals(List<HospitalDTO> hospitalDTOs);
}
