package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.ClinicDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Clinic and its DTO ClinicDTO.
 */
@Mapper(componentModel = "spring", uses = {HospitalMapper.class, DepartmentMapper.class})
public interface ClinicMapper {

    ClinicDTO clinicToClinicDTO(Clinic clinic);

    List<ClinicDTO> clinicsToClinicDTOs(List<Clinic> clinics);

    @Mapping(target = "departments")
    Clinic clinicDTOToClinic(ClinicDTO clinicDTO);

    List<Clinic> clinicDTOsToClinics(List<ClinicDTO> clinicDTOs);

    default Hospital hospitalFromId(Long id) {
        if (id == null) {
            return null;
        }
        Hospital hospital = new Hospital();
        hospital.setId(id);
        return hospital;
    }
}
