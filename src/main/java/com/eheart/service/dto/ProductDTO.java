package com.eheart.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String img;

    private String link;

    private Double price;

    private String shelfLife;

    private ZonedDateTime produceDate;

    private String producer;

    private String produceLoaction;

    private String guige;

    private String chengfen;

    private String yongfa;

    private String xingzhuang;

    private String gongneng;

    private String pihao;

    private String buliangfanying;

    private String notes;

    private Integer inventory;

    private Integer total;

    private Boolean isNew;

    private Boolean favorite;

    private String productPlaceholder1;

    private String productPlaceholder2;

    private String productPlaceholder3;

    private ZonedDateTime createdDate;

    private String createdBy;

    private ZonedDateTime lastModifiedDate;

    private String lastModifiedBy;


    private Set<ProductCategoryDTO> myCategorys = new HashSet<>();

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
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }
    public ZonedDateTime getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(ZonedDateTime produceDate) {
        this.produceDate = produceDate;
    }
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
    public String getProduceLoaction() {
        return produceLoaction;
    }

    public void setProduceLoaction(String produceLoaction) {
        this.produceLoaction = produceLoaction;
    }
    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }
    public String getChengfen() {
        return chengfen;
    }

    public void setChengfen(String chengfen) {
        this.chengfen = chengfen;
    }
    public String getYongfa() {
        return yongfa;
    }

    public void setYongfa(String yongfa) {
        this.yongfa = yongfa;
    }
    public String getXingzhuang() {
        return xingzhuang;
    }

    public void setXingzhuang(String xingzhuang) {
        this.xingzhuang = xingzhuang;
    }
    public String getGongneng() {
        return gongneng;
    }

    public void setGongneng(String gongneng) {
        this.gongneng = gongneng;
    }
    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao;
    }
    public String getBuliangfanying() {
        return buliangfanying;
    }

    public void setBuliangfanying(String buliangfanying) {
        this.buliangfanying = buliangfanying;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
    public String getProductPlaceholder1() {
        return productPlaceholder1;
    }

    public void setProductPlaceholder1(String productPlaceholder1) {
        this.productPlaceholder1 = productPlaceholder1;
    }
    public String getProductPlaceholder2() {
        return productPlaceholder2;
    }

    public void setProductPlaceholder2(String productPlaceholder2) {
        this.productPlaceholder2 = productPlaceholder2;
    }
    public String getProductPlaceholder3() {
        return productPlaceholder3;
    }

    public void setProductPlaceholder3(String productPlaceholder3) {
        this.productPlaceholder3 = productPlaceholder3;
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

    public Set<ProductCategoryDTO> getMyCategorys() {
        return myCategorys;
    }

    public void setMyCategorys(Set<ProductCategoryDTO> productCategories) {
        this.myCategorys = productCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;

        if ( ! Objects.equals(id, productDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", img='" + img + "'" +
            ", link='" + link + "'" +
            ", price='" + price + "'" +
            ", shelfLife='" + shelfLife + "'" +
            ", produceDate='" + produceDate + "'" +
            ", producer='" + producer + "'" +
            ", produceLoaction='" + produceLoaction + "'" +
            ", guige='" + guige + "'" +
            ", chengfen='" + chengfen + "'" +
            ", yongfa='" + yongfa + "'" +
            ", xingzhuang='" + xingzhuang + "'" +
            ", gongneng='" + gongneng + "'" +
            ", pihao='" + pihao + "'" +
            ", buliangfanying='" + buliangfanying + "'" +
            ", notes='" + notes + "'" +
            ", inventory='" + inventory + "'" +
            ", total='" + total + "'" +
            ", isNew='" + isNew + "'" +
            ", favorite='" + favorite + "'" +
            ", productPlaceholder1='" + productPlaceholder1 + "'" +
            ", productPlaceholder2='" + productPlaceholder2 + "'" +
            ", productPlaceholder3='" + productPlaceholder3 + "'" +
            ", createdDate='" + createdDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", lastModifiedDate='" + lastModifiedDate + "'" +
            ", lastModifiedBy='" + lastModifiedBy + "'" +
            '}';
    }
}
