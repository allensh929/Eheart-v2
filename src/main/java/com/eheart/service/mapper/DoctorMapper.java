package com.eheart.service.mapper;

import com.eheart.domain.*;
import com.eheart.service.dto.DoctorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Doctor and its DTO DoctorDTO.
 */
@Mapper(componentModel = "spring", uses = {DomainMapper.class, })
public interface DoctorMapper {

    @Mapping(source = "myTitle.id", target = "myTitleId")
    @Mapping(source = "myHospital.id", target = "myHospitalId")
    @Mapping(source = "myDepartment.id", target = "myDepartmentId")
    DoctorDTO doctorToDoctorDTO(Doctor doctor);

    List<DoctorDTO> doctorsToDoctorDTOs(List<Doctor> doctors);

    @Mapping(source = "myTitleId", target = "myTitle")
    @Mapping(source = "myHospitalId", target = "myHospital")
    @Mapping(source = "myDepartmentId", target = "myDepartment")
    Doctor doctorDTOToDoctor(DoctorDTO doctorDTO);

    List<Doctor> doctorDTOsToDoctors(List<DoctorDTO> doctorDTOs);

    default Title titleFromId(Long id) {
        if (id == null) {
            return null;
        }
        Title title = new Title();
        title.setId(id);
        return title;
    }

    default Hospital hospitalFromId(Long id) {
        if (id == null) {
            return null;
        }
        Hospital hospital = new Hospital();
        hospital.setId(id);
        return hospital;
    }

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }

    default Domain domainFromId(Long id) {
        if (id == null) {
            return null;
        }
        Domain domain = new Domain();
        domain.setId(id);
        return domain;
    }
}
