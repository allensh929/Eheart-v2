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
 * A Domain.
 */
@Entity
@Table(name = "domain")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "domain")
public class Domain extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "domain_placeholder_1")
    private String domainPlaceholder1;

    @Column(name = "domain_placeholder_2")
    private String domainPlaceholder2;

    @Column(name = "domain_placeholder_3")
    private String domainPlaceholder3;

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

    @ManyToMany(mappedBy = "domains")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Doctor> doctors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Domain name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomainPlaceholder1() {
        return domainPlaceholder1;
    }

    public Domain domainPlaceholder1(String domainPlaceholder1) {
        this.domainPlaceholder1 = domainPlaceholder1;
        return this;
    }

    public void setDomainPlaceholder1(String domainPlaceholder1) {
        this.domainPlaceholder1 = domainPlaceholder1;
    }

    public String getDomainPlaceholder2() {
        return domainPlaceholder2;
    }

    public Domain domainPlaceholder2(String domainPlaceholder2) {
        this.domainPlaceholder2 = domainPlaceholder2;
        return this;
    }

    public void setDomainPlaceholder2(String domainPlaceholder2) {
        this.domainPlaceholder2 = domainPlaceholder2;
    }

    public String getDomainPlaceholder3() {
        return domainPlaceholder3;
    }

    public Domain domainPlaceholder3(String domainPlaceholder3) {
        this.domainPlaceholder3 = domainPlaceholder3;
        return this;
    }

    public void setDomainPlaceholder3(String domainPlaceholder3) {
        this.domainPlaceholder3 = domainPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Domain createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Domain createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Domain lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Domain lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Domain doctors(Set<Doctor> doctors) {
        this.doctors = doctors;
        return this;
    }

    public Domain addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.getDomains().add(this);
        return this;
    }

    public Domain removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
        doctor.getDomains().remove(this);
        return this;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Domain domain = (Domain) o;
        if (domain.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, domain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Domain{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", domainPlaceholder1='" + domainPlaceholder1 + "'" +
            ", domainPlaceholder2='" + domainPlaceholder2 + "'" +
            ", domainPlaceholder3='" + domainPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
