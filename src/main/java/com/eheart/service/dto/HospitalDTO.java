package com.eheart.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Hospital entity.
 */
public class HospitalDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String img;

    private String hospitalPlaceholder1;

    private String hospitalPlaceholder2;

    private String hospitalPlaceholder3;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getHospitalPlaceholder1() {
        return hospitalPlaceholder1;
    }

    public void setHospitalPlaceholder1(String hospitalPlaceholder1) {
        this.hospitalPlaceholder1 = hospitalPlaceholder1;
    }
    public String getHospitalPlaceholder2() {
        return hospitalPlaceholder2;
    }

    public void setHospitalPlaceholder2(String hospitalPlaceholder2) {
        this.hospitalPlaceholder2 = hospitalPlaceholder2;
    }
    public String getHospitalPlaceholder3() {
        return hospitalPlaceholder3;
    }

    public void setHospitalPlaceholder3(String hospitalPlaceholder3) {
        this.hospitalPlaceholder3 = hospitalPlaceholder3;
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

        HospitalDTO hospitalDTO = (HospitalDTO) o;

        if ( ! Objects.equals(id, hospitalDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HospitalDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", img='" + img + "'" +
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
