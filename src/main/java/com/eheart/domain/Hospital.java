package com.eheart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Hospital.
 */
@Entity
@Table(name = "hospital")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hospital")
public class Hospital extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "hospital_placeholder_1")
    private String hospitalPlaceholder1;

    @Column(name = "hospital_placeholder_2")
    private String hospitalPlaceholder2;

    @Column(name = "hospital_placeholder_3")
    private String hospitalPlaceholder3;

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

    @ManyToMany(mappedBy = "hospitals")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Department> departments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Hospital name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Hospital description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHospitalPlaceholder1() {
        return hospitalPlaceholder1;
    }

    public Hospital hospitalPlaceholder1(String hospitalPlaceholder1) {
        this.hospitalPlaceholder1 = hospitalPlaceholder1;
        return this;
    }

    public void setHospitalPlaceholder1(String hospitalPlaceholder1) {
        this.hospitalPlaceholder1 = hospitalPlaceholder1;
    }

    public String getHospitalPlaceholder2() {
        return hospitalPlaceholder2;
    }

    public Hospital hospitalPlaceholder2(String hospitalPlaceholder2) {
        this.hospitalPlaceholder2 = hospitalPlaceholder2;
        return this;
    }

    public void setHospitalPlaceholder2(String hospitalPlaceholder2) {
        this.hospitalPlaceholder2 = hospitalPlaceholder2;
    }

    public String getHospitalPlaceholder3() {
        return hospitalPlaceholder3;
    }

    public Hospital hospitalPlaceholder3(String hospitalPlaceholder3) {
        this.hospitalPlaceholder3 = hospitalPlaceholder3;
        return this;
    }

    public void setHospitalPlaceholder3(String hospitalPlaceholder3) {
        this.hospitalPlaceholder3 = hospitalPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Hospital createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Hospital createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Hospital lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Hospital lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Hospital departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Hospital addDepartment(Department department) {
        departments.add(department);
        department.getHospitals().add(this);
        return this;
    }

    public Hospital removeDepartment(Department department) {
        departments.remove(department);
        department.getHospitals().remove(this);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hospital hospital = (Hospital) o;
        if (hospital.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hospital.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hospital{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", hospitalPlaceholder1='" + hospitalPlaceholder1 + "'" +
            ", hospitalPlaceholder2='" + hospitalPlaceholder2 + "'" +
            ", hospitalPlaceholder3='" + hospitalPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
