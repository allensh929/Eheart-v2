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
 * A ProductSubCategory.
 */
@Entity
@Table(name = "product_sub_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productsubcategory")
public class ProductSubCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img;

    @Column(name = "sub_category_placeholder_1")
    private String subCategoryPlaceholder1;

    @Column(name = "sub_category_placeholder_2")
    private String subCategoryPlaceholder2;

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
    private ProductCategory superCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductSubCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductSubCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public ProductSubCategory img(String img) {
        this.img = img;
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSubCategoryPlaceholder1() {
        return subCategoryPlaceholder1;
    }

    public ProductSubCategory subCategoryPlaceholder1(String subCategoryPlaceholder1) {
        this.subCategoryPlaceholder1 = subCategoryPlaceholder1;
        return this;
    }

    public void setSubCategoryPlaceholder1(String subCategoryPlaceholder1) {
        this.subCategoryPlaceholder1 = subCategoryPlaceholder1;
    }

    public String getSubCategoryPlaceholder2() {
        return subCategoryPlaceholder2;
    }

    public ProductSubCategory subCategoryPlaceholder2(String subCategoryPlaceholder2) {
        this.subCategoryPlaceholder2 = subCategoryPlaceholder2;
        return this;
    }

    public void setSubCategoryPlaceholder2(String subCategoryPlaceholder2) {
        this.subCategoryPlaceholder2 = subCategoryPlaceholder2;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ProductSubCategory createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ProductSubCategory createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ProductSubCategory lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ProductSubCategory lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ProductCategory getSuperCategory() {
        return superCategory;
    }

    public ProductSubCategory superCategory(ProductCategory productCategory) {
        this.superCategory = productCategory;
        return this;
    }

    public void setSuperCategory(ProductCategory productCategory) {
        this.superCategory = productCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductSubCategory productSubCategory = (ProductSubCategory) o;
        if (productSubCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productSubCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductSubCategory{" +
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
