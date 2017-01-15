package com.eheart.service.dto;

import com.eheart.domain.ProductSubCategory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * A DTO for the ProductCategory entity.
 */
public class ProductCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String img;

    private String categoryPlaceholder1;

    private String categoryPlaceholder2;

    private String categoryPlaceholder3;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime lastModifiedDate;

    private String lastModifiedBy;

    private Set<ProductDTO> hasProducts = new HashSet<>();

    private Set<ProductSubCategoryDTO> hasSubCategories = new HashSet<>();

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
    public String getCategoryPlaceholder1() {
        return categoryPlaceholder1;
    }

    public void setCategoryPlaceholder1(String categoryPlaceholder1) {
        this.categoryPlaceholder1 = categoryPlaceholder1;
    }
    public String getCategoryPlaceholder2() {
        return categoryPlaceholder2;
    }

    public void setCategoryPlaceholder2(String categoryPlaceholder2) {
        this.categoryPlaceholder2 = categoryPlaceholder2;
    }
    public String getCategoryPlaceholder3() {
        return categoryPlaceholder3;
    }

    public void setCategoryPlaceholder3(String categoryPlaceholder3) {
        this.categoryPlaceholder3 = categoryPlaceholder3;
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

    public Set<ProductDTO> getHasProducts() {
        return hasProducts;
    }

    public void setHasProducts(Set<ProductDTO> hasProducts) {
        this.hasProducts = hasProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCategoryDTO productCategoryDTO = (ProductCategoryDTO) o;

        if ( ! Objects.equals(id, productCategoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", img='" + img + "'" +
            ", categoryPlaceholder1='" + categoryPlaceholder1 + "'" +
            ", categoryPlaceholder2='" + categoryPlaceholder2 + "'" +
            ", categoryPlaceholder3='" + categoryPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
