package com.eheart.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Domain entity.
 */
public class DomainDTO implements Serializable {

    private Long id;

    private String name;

    private String domainPlaceholder1;

    private String domainPlaceholder2;

    private String domainPlaceholder3;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime lastModifiedDate;

    private String lastModifiedBy;


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
    public String getDomainPlaceholder1() {
        return domainPlaceholder1;
    }

    public void setDomainPlaceholder1(String domainPlaceholder1) {
        this.domainPlaceholder1 = domainPlaceholder1;
    }
    public String getDomainPlaceholder2() {
        return domainPlaceholder2;
    }

    public void setDomainPlaceholder2(String domainPlaceholder2) {
        this.domainPlaceholder2 = domainPlaceholder2;
    }
    public String getDomainPlaceholder3() {
        return domainPlaceholder3;
    }

    public void setDomainPlaceholder3(String domainPlaceholder3) {
        this.domainPlaceholder3 = domainPlaceholder3;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DomainDTO domainDTO = (DomainDTO) o;

        if ( ! Objects.equals(id, domainDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DomainDTO{" +
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
