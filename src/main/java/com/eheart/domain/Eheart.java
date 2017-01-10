package com.eheart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Eheart.
 */
@Entity
@Table(name = "eheart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eheart")
public class Eheart extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "address")
    private String address;

    @Column(name = "wechat")
    private String wechat;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "eheart_placeholder_1")
    private String eheartPlaceholder1;

    @Column(name = "eheart_placeholder_2")
    private String eheartPlaceholder2;

    @Column(name = "eheart_placeholder_3")
    private String eheartPlaceholder3;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Eheart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Eheart description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public Eheart logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public Eheart email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Eheart phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public Eheart fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public Eheart address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWechat() {
        return wechat;
    }

    public Eheart wechat(String wechat) {
        this.wechat = wechat;
        return this;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getCopyright() {
        return copyright;
    }

    public Eheart copyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getEheartPlaceholder1() {
        return eheartPlaceholder1;
    }

    public Eheart eheartPlaceholder1(String eheartPlaceholder1) {
        this.eheartPlaceholder1 = eheartPlaceholder1;
        return this;
    }

    public void setEheartPlaceholder1(String eheartPlaceholder1) {
        this.eheartPlaceholder1 = eheartPlaceholder1;
    }

    public String getEheartPlaceholder2() {
        return eheartPlaceholder2;
    }

    public Eheart eheartPlaceholder2(String eheartPlaceholder2) {
        this.eheartPlaceholder2 = eheartPlaceholder2;
        return this;
    }

    public void setEheartPlaceholder2(String eheartPlaceholder2) {
        this.eheartPlaceholder2 = eheartPlaceholder2;
    }

    public String getEheartPlaceholder3() {
        return eheartPlaceholder3;
    }

    public Eheart eheartPlaceholder3(String eheartPlaceholder3) {
        this.eheartPlaceholder3 = eheartPlaceholder3;
        return this;
    }

    public void setEheartPlaceholder3(String eheartPlaceholder3) {
        this.eheartPlaceholder3 = eheartPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Eheart createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Eheart createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Eheart lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Eheart lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
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
        Eheart eheart = (Eheart) o;
        if (eheart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eheart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Eheart{" +
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
