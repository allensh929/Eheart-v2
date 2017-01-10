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
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "doctor")
public class Doctor extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "experience")
    private String experience;

    @Column(name = "working_age")
    private Integer workingAge;

    @Column(name = "domain_custom")
    private String domainCustom;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "doctor_placeholder_1")
    private String doctorPlaceholder1;

    @Column(name = "doctor_placeholder_2")
    private String doctorPlaceholder2;

    @Column(name = "doctor_placeholder_3")
    private String doctorPlaceholder3;

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

    @ManyToOne
    private Title myTitle;

    @ManyToOne
    private Hospital myHospital;

    @ManyToOne
    private Department myDepartment;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "doctor_domain",
               joinColumns = @JoinColumn(name="doctors_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="domains_id", referencedColumnName="ID"))
    private Set<Domain> domains = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Doctor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Doctor age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public Doctor experience(String experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Integer getWorkingAge() {
        return workingAge;
    }

    public Doctor workingAge(Integer workingAge) {
        this.workingAge = workingAge;
        return this;
    }

    public void setWorkingAge(Integer workingAge) {
        this.workingAge = workingAge;
    }

    public String getDomainCustom() {
        return domainCustom;
    }

    public Doctor domainCustom(String domainCustom) {
        this.domainCustom = domainCustom;
        return this;
    }

    public void setDomainCustom(String domainCustom) {
        this.domainCustom = domainCustom;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Doctor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public Doctor description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorPlaceholder1() {
        return doctorPlaceholder1;
    }

    public Doctor doctorPlaceholder1(String doctorPlaceholder1) {
        this.doctorPlaceholder1 = doctorPlaceholder1;
        return this;
    }

    public void setDoctorPlaceholder1(String doctorPlaceholder1) {
        this.doctorPlaceholder1 = doctorPlaceholder1;
    }

    public String getDoctorPlaceholder2() {
        return doctorPlaceholder2;
    }

    public Doctor doctorPlaceholder2(String doctorPlaceholder2) {
        this.doctorPlaceholder2 = doctorPlaceholder2;
        return this;
    }

    public void setDoctorPlaceholder2(String doctorPlaceholder2) {
        this.doctorPlaceholder2 = doctorPlaceholder2;
    }

    public String getDoctorPlaceholder3() {
        return doctorPlaceholder3;
    }

    public Doctor doctorPlaceholder3(String doctorPlaceholder3) {
        this.doctorPlaceholder3 = doctorPlaceholder3;
        return this;
    }

    public void setDoctorPlaceholder3(String doctorPlaceholder3) {
        this.doctorPlaceholder3 = doctorPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Doctor createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Doctor createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Doctor lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Doctor lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Title getMyTitle() {
        return myTitle;
    }

    public Doctor myTitle(Title title) {
        this.myTitle = title;
        return this;
    }

    public void setMyTitle(Title title) {
        this.myTitle = title;
    }

    public Hospital getMyHospital() {
        return myHospital;
    }

    public Doctor myHospital(Hospital hospital) {
        this.myHospital = hospital;
        return this;
    }

    public void setMyHospital(Hospital hospital) {
        this.myHospital = hospital;
    }

    public Department getMyDepartment() {
        return myDepartment;
    }

    public Doctor myDepartment(Department department) {
        this.myDepartment = department;
        return this;
    }

    public void setMyDepartment(Department department) {
        this.myDepartment = department;
    }

    public Set<Domain> getDomains() {
        return domains;
    }

    public Doctor domains(Set<Domain> domains) {
        this.domains = domains;
        return this;
    }

    public Doctor addDomain(Domain domain) {
        domains.add(domain);
        domain.getDoctors().add(this);
        return this;
    }

    public Doctor removeDomain(Domain domain) {
        domains.remove(domain);
        domain.getDoctors().remove(this);
        return this;
    }

    public void setDomains(Set<Domain> domains) {
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
        Doctor doctor = (Doctor) o;
        if (doctor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Doctor{" +
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
            ", doctorPlaceholder3='" + doctorPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
