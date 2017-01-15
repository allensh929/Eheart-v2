package com.eheart.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ProductSubCategory entity.
 */
public class ProductSubCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String img;

    private String subCategoryPlaceholder1;

    private String subCategoryPlaceholder2;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime lastModifiedDate;

    private String lastModifiedBy;


    private Long superCategoryId;
    
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
    public String getSubCategoryPlaceholder1() {
        return subCategoryPlaceholder1;
    }

    public void setSubCategoryPlaceholder1(String subCategoryPlaceholder1) {
        this.subCategoryPlaceholder1 = subCategoryPlaceholder1;
    }
    public String getSubCategoryPlaceholder2() {
        return subCategoryPlaceholder2;
    }

    public void setSubCategoryPlaceholder2(String subCategoryPlaceholder2) {
        this.subCategoryPlaceholder2 = subCategoryPlaceholder2;
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

    public Long getSuperCategoryId() {
        return superCategoryId;
    }

    public void setSuperCategoryId(Long productCategoryId) {
        this.superCategoryId = productCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductSubCategoryDTO productSubCategoryDTO = (ProductSubCategoryDTO) o;

        if ( ! Objects.equals(id, productSubCategoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductSubCategoryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", img='" + img + "'" +
            ", subCategoryPlaceholder1='" + subCategoryPlaceholder1 + "'" +
            ", subCategoryPlaceholder2='" + subCategoryPlaceholder2 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
