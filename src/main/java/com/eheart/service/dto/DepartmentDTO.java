package com.eheart.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Department entity.
 */
public class DepartmentDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String departmentPlaceholder1;

    private String departmentPlaceholder2;

    private String departmentPlaceholder3;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime lastModifiedDate;

    private String lastModifiedBy;


    private Set<HospitalDTO> hospitals = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDepartmentPlaceholder1() {
        return departmentPlaceholder1;
    }

    public void setDepartmentPlaceholder1(String departmentPlaceholder1) {
        this.departmentPlaceholder1 = departmentPlaceholder1;
    }
    public String getDepartmentPlaceholder2() {
        return departmentPlaceholder2;
    }

    public void setDepartmentPlaceholder2(String departmentPlaceholder2) {
        this.departmentPlaceholder2 = departmentPlaceholder2;
    }
    public String getDepartmentPlaceholder3() {
        return departmentPlaceholder3;
    }

    public void setDepartmentPlaceholder3(String departmentPlaceholder3) {
        this.departmentPlaceholder3 = departmentPlaceholder3;
    }
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<HospitalDTO> getHospitals() {
        return hospitals;
    }

    public void setHospitals(Set<HospitalDTO> hospitals) {
        this.hospitals = hospitals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepartmentDTO departmentDTO = (DepartmentDTO) o;

        if ( ! Objects.equals(id, departmentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", departmentPlaceholder1='" + departmentPlaceholder1 + "'" +
            ", departmentPlaceholder2='" + departmentPlaceholder2 + "'" +
            ", departmentPlaceholder3='" + departmentPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
