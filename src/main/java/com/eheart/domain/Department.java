package com.eheart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "department")
public class Department extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "department_placeholder_1")
    private String departmentPlaceholder1;

    @Column(name = "department_placeholder_2")
    private String departmentPlaceholder2;

    @Column(name = "department_placeholder_3")
    private String departmentPlaceholder3;

//    @Column(name = "created_date")
//    private ZonedDateTime createdDate;
//
//    @Column(name = "created_by")
//    private String createdBy;
//
//    @Column(name = "last_modified_date")
//    private ZonedDateTime lastModifiedDate;
//
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "department_hospital",
               joinColumns = @JoinColumn(name="departments_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="hospitals_id", referencedColumnName="ID"))
    private Set<Hospital> hospitals = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Department description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartmentPlaceholder1() {
        return departmentPlaceholder1;
    }

    public Department departmentPlaceholder1(String departmentPlaceholder1) {
        this.departmentPlaceholder1 = departmentPlaceholder1;
        return this;
    }

    public void setDepartmentPlaceholder1(String departmentPlaceholder1) {
        this.departmentPlaceholder1 = departmentPlaceholder1;
    }

    public String getDepartmentPlaceholder2() {
        return departmentPlaceholder2;
    }

    public Department departmentPlaceholder2(String departmentPlaceholder2) {
        this.departmentPlaceholder2 = departmentPlaceholder2;
        return this;
    }

    public void setDepartmentPlaceholder2(String departmentPlaceholder2) {
        this.departmentPlaceholder2 = departmentPlaceholder2;
    }

    public String getDepartmentPlaceholder3() {
        return departmentPlaceholder3;
    }

    public Department departmentPlaceholder3(String departmentPlaceholder3) {
        this.departmentPlaceholder3 = departmentPlaceholder3;
        return this;
    }

    public void setDepartmentPlaceholder3(String departmentPlaceholder3) {
        this.departmentPlaceholder3 = departmentPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Department createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Department createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Department lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Department lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Hospital> getHospitals() {
        return hospitals;
    }

    public Department hospitals(Set<Hospital> hospitals) {
        this.hospitals = hospitals;
        return this;
    }

    public Department addHospital(Hospital hospital) {
        hospitals.add(hospital);
        hospital.getDepartments().add(this);
        return this;
    }

    public Department removeHospital(Hospital hospital) {
        hospitals.remove(hospital);
        hospital.getDepartments().remove(this);
        return this;
    }

    public void setHospitals(Set<Hospital> hospitals) {
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
        Department department = (Department) o;
        if (department.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, department.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
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
