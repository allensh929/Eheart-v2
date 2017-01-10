package com.eheart.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Title entity.
 */
public class TitleDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String titlePlaceholder1;

    private String titlePlaceholder2;

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
    public String getTitlePlaceholder1() {
        return titlePlaceholder1;
    }

    public void setTitlePlaceholder1(String titlePlaceholder1) {
        this.titlePlaceholder1 = titlePlaceholder1;
    }
    public String getTitlePlaceholder2() {
        return titlePlaceholder2;
    }

    public void setTitlePlaceholder2(String titlePlaceholder2) {
        this.titlePlaceholder2 = titlePlaceholder2;
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

        TitleDTO titleDTO = (TitleDTO) o;

        if ( ! Objects.equals(id, titleDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TitleDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", titlePlaceholder1='" + titlePlaceholder1 + "'" +
            ", titlePlaceholder2='" + titlePlaceholder2 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
