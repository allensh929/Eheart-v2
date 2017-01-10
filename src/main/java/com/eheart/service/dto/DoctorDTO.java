package com.eheart.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Doctor entity.
 */
public class DoctorDTO implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private String experience;

    private Integer workingAge;

    private String domainCustom;

    private String phone;

    private String email;

    private String description;

    private String doctorPlaceholder1;

    private String doctorPlaceholder2;

    private String photo;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime lastModifiedDate;

    private String lastModifiedBy;


    private Long myTitleId;

    private Long myHospitalId;

    private Long myDepartmentId;

    private Set<DomainDTO> domains = new HashSet<>();

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
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
    public Integer getWorkingAge() {
        return workingAge;
    }

    public void setWorkingAge(Integer workingAge) {
        this.workingAge = workingAge;
    }
    public String getDomainCustom() {
        return domainCustom;
    }

    public void setDomainCustom(String domainCustom) {
        this.domainCustom = domainCustom;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDoctorPlaceholder1() {
        return doctorPlaceholder1;
    }

    public void setDoctorPlaceholder1(String doctorPlaceholder1) {
        this.doctorPlaceholder1 = doctorPlaceholder1;
    }
    public String getDoctorPlaceholder2() {
        return doctorPlaceholder2;
    }

    public void setDoctorPlaceholder2(String doctorPlaceholder2) {
        this.doctorPlaceholder2 = doctorPlaceholder2;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public Long getMyTitleId() {
        return myTitleId;
    }

    public void setMyTitleId(Long titleId) {
        this.myTitleId = titleId;
    }

    public Long getMyHospitalId() {
        return myHospitalId;
    }

    public void setMyHospitalId(Long hospitalId) {
        this.myHospitalId = hospitalId;
    }

    public Long getMyDepartmentId() {
        return myDepartmentId;
    }

    public void setMyDepartmentId(Long departmentId) {
        this.myDepartmentId = departmentId;
    }

    public Set<DomainDTO> getDomains() {
        return domains;
    }

    public void setDomains(Set<DomainDTO> domains) {
        this.domains = domains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoctorDTO doctorDTO = (DoctorDTO) o;

        if ( ! Objects.equals(id, doctorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DoctorDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", age='" + age + "'" +
            ", experience='" + experience + "'" +
            ", workingAge='" + workingAge + "'" +
            ", domainCustom='" + domainCustom + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", description='" + description + "'" +
            ", doctorPlaceholder1='" + doctorPlaceholder1 + "'" +
            ", doctorPlaceholder2='" + doctorPlaceholder2 + "'" +
            ", photo='" + photo + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
