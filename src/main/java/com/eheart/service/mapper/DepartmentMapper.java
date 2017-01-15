package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.DepartmentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {HospitalMapper.class, ClinicMapper.class})
public interface DepartmentMapper {

    @Mapping(source = "clinic.id", target = "clinicId")
    DepartmentDTO departmentToDepartmentDTO(Department department);

    List<DepartmentDTO> departmentsToDepartmentDTOs(List<Department> departments);

    @Mapping(source = "clinicId", target = "clinic")
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);

    List<Department> departmentDTOsToDepartments(List<DepartmentDTO> departmentDTOs);

    default Hospital hospitalFromId(Long id) {
        if (id == null) {
            return null;
        }
        Hospital hospital = new Hospital();
        hospital.setId(id);
        return hospital;
    }

    default Clinic clinicFromId(Long id) {
        if (id == null) {
            return null;
        }
        Clinic clinic = new Clinic();
        clinic.setId(id);
        return clinic;
    }
}
