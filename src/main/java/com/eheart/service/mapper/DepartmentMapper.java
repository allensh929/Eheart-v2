package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.DepartmentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {HospitalMapper.class, })
public interface DepartmentMapper {

    DepartmentDTO departmentToDepartmentDTO(Department department);

    List<DepartmentDTO> departmentsToDepartmentDTOs(List<Department> departments);

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
}
