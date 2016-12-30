package com.eheart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productcategory")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category_placeholder_1")
    private String categoryPlaceholder1;

    @Column(name = "category_placeholder_2")
    private String categoryPlaceholder2;

    @Column(name = "category_placeholder_3")
    private String categoryPlaceholder3;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private ZonedDateTime lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToMany(mappedBy = "myCategorys")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> hasProducts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProductCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryPlaceholder1() {
        return categoryPlaceholder1;
    }

    public ProductCategory categoryPlaceholder1(String categoryPlaceholder1) {
        this.categoryPlaceholder1 = categoryPlaceholder1;
        return this;
    }

    public void setCategoryPlaceholder1(String categoryPlaceholder1) {
        this.categoryPlaceholder1 = categoryPlaceholder1;
    }

    public String getCategoryPlaceholder2() {
        return categoryPlaceholder2;
    }

    public ProductCategory categoryPlaceholder2(String categoryPlaceholder2) {
        this.categoryPlaceholder2 = categoryPlaceholder2;
        return this;
    }

    public void setCategoryPlaceholder2(String categoryPlaceholder2) {
        this.categoryPlaceholder2 = categoryPlaceholder2;
    }

    public String getCategoryPlaceholder3() {
        return categoryPlaceholder3;
    }

    public ProductCategory categoryPlaceholder3(String categoryPlaceholder3) {
        this.categoryPlaceholder3 = categoryPlaceholder3;
        return this;
    }

    public void setCategoryPlaceholder3(String categoryPlaceholder3) {
        this.categoryPlaceholder3 = categoryPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ProductCategory createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ProductCategory createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ProductCategory lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ProductCategory lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Product> getHasProducts() {
        return hasProducts;
    }

    public ProductCategory hasProducts(Set<Product> products) {
        this.hasProducts = products;
        return this;
    }

    public ProductCategory addHasProducts(Product product) {
        hasProducts.add(product);
        product.getMyCategorys().add(this);
        return this;
    }

    public ProductCategory removeHasProducts(Product product) {
        hasProducts.remove(product);
        product.getMyCategorys().remove(this);
        return this;
    }

    public void setHasProducts(Set<Product> products) {
        this.hasProducts = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCategory productCategory = (ProductCategory) o;
        if (productCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, productCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
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
