package com.eheart.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Eheart entity.
 */
public class EheartDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String logo;

    private String email;

    private String phone;

    private String fax;

    private String address;

    private String wechat;

    private String copyright;

    private String eheartPlaceholder1;

    private String eheartPlaceholder2;

    private String eheartPlaceholder3;

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
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    public String getEheartPlaceholder1() {
        return eheartPlaceholder1;
    }

    public void setEheartPlaceholder1(String eheartPlaceholder1) {
        this.eheartPlaceholder1 = eheartPlaceholder1;
    }
    public String getEheartPlaceholder2() {
        return eheartPlaceholder2;
    }

    public void setEheartPlaceholder2(String eheartPlaceholder2) {
        this.eheartPlaceholder2 = eheartPlaceholder2;
    }
    public String getEheartPlaceholder3() {
        return eheartPlaceholder3;
    }

    public void setEheartPlaceholder3(String eheartPlaceholder3) {
        this.eheartPlaceholder3 = eheartPlaceholder3;
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

        EheartDTO eheartDTO = (EheartDTO) o;

        if ( ! Objects.equals(id, eheartDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EheartDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", logo='" + logo + "'" +
            ", email='" + email + "'" +
            ", phone='" + phone + "'" +
            ", fax='" + fax + "'" +
            ", address='" + address + "'" +
            ", wechat='" + wechat + "'" +
            ", copyright='" + copyright + "'" +
            ", eheartPlaceholder1='" + eheartPlaceholder1 + "'" +
            ", eheartPlaceholder2='" + eheartPlaceholder2 + "'" +
            ", eheartPlaceholder3='" + eheartPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
