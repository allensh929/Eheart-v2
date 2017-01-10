package com.eheart.domain;

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
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
public class Product extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "link")
    private String link;

    @Column(name = "price")
    private Double price;

    @Column(name = "shelf_life")
    private String shelfLife;

    @Column(name = "produce_date")
    private ZonedDateTime produceDate;

    @Column(name = "producer")
    private String producer;

    @Column(name = "produce_loaction")
    private String produceLoaction;

    @Column(name = "guige")
    private String guige;

    @Column(name = "chengfen")
    private String chengfen;

    @Column(name = "yongfa")
    private String yongfa;

    @Column(name = "xingzhuang")
    private String xingzhuang;

    @Column(name = "gongneng")
    private String gongneng;

    @Column(name = "pihao")
    private String pihao;

    @Column(name = "buliangfanying")
    private String buliangfanying;

    @Column(name = "notes")
    private String notes;

    @Column(name = "inventory")
    private Integer inventory;

    @Column(name = "total")
    private Integer total;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "favorite")
    private Boolean favorite;

    @Column(name = "product_placeholder_1")
    private String productPlaceholder1;

    @Column(name = "product_placeholder_2")
    private String productPlaceholder2;

    @Column(name = "product_placeholder_3")
    private String productPlaceholder3;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_my_categorys",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="my_categorys_id", referencedColumnName="ID"))
    private Set<ProductCategory> myCategorys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public Product img(String img) {
        this.img = img;
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public Product link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public Product shelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
        return this;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public ZonedDateTime getProduceDate() {
        return produceDate;
    }

    public Product produceDate(ZonedDateTime produceDate) {
        this.produceDate = produceDate;
        return this;
    }

    public void setProduceDate(ZonedDateTime produceDate) {
        this.produceDate = produceDate;
    }

    public String getProducer() {
        return producer;
    }

    public Product producer(String producer) {
        this.producer = producer;
        return this;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProduceLoaction() {
        return produceLoaction;
    }

    public Product produceLoaction(String produceLoaction) {
        this.produceLoaction = produceLoaction;
        return this;
    }

    public void setProduceLoaction(String produceLoaction) {
        this.produceLoaction = produceLoaction;
    }

    public String getGuige() {
        return guige;
    }

    public Product guige(String guige) {
        this.guige = guige;
        return this;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public String getChengfen() {
        return chengfen;
    }

    public Product chengfen(String chengfen) {
        this.chengfen = chengfen;
        return this;
    }

    public void setChengfen(String chengfen) {
        this.chengfen = chengfen;
    }

    public String getYongfa() {
        return yongfa;
    }

    public Product yongfa(String yongfa) {
        this.yongfa = yongfa;
        return this;
    }

    public void setYongfa(String yongfa) {
        this.yongfa = yongfa;
    }

    public String getXingzhuang() {
        return xingzhuang;
    }

    public Product xingzhuang(String xingzhuang) {
        this.xingzhuang = xingzhuang;
        return this;
    }

    public void setXingzhuang(String xingzhuang) {
        this.xingzhuang = xingzhuang;
    }

    public String getGongneng() {
        return gongneng;
    }

    public Product gongneng(String gongneng) {
        this.gongneng = gongneng;
        return this;
    }

    public void setGongneng(String gongneng) {
        this.gongneng = gongneng;
    }

    public String getPihao() {
        return pihao;
    }

    public Product pihao(String pihao) {
        this.pihao = pihao;
        return this;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao;
    }

    public String getBuliangfanying() {
        return buliangfanying;
    }

    public Product buliangfanying(String buliangfanying) {
        this.buliangfanying = buliangfanying;
        return this;
    }

    public void setBuliangfanying(String buliangfanying) {
        this.buliangfanying = buliangfanying;
    }

    public String getNotes() {
        return notes;
    }

    public Product notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getInventory() {
        return inventory;
    }

    public Product inventory(Integer inventory) {
        this.inventory = inventory;
        return this;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getTotal() {
        return total;
    }

    public Product total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean isIsNew() {
        return isNew;
    }

    public Product isNew(Boolean isNew) {
        this.isNew = isNew;
        return this;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public Product favorite(Boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getProductPlaceholder1() {
        return productPlaceholder1;
    }

    public Product productPlaceholder1(String productPlaceholder1) {
        this.productPlaceholder1 = productPlaceholder1;
        return this;
    }

    public void setProductPlaceholder1(String productPlaceholder1) {
        this.productPlaceholder1 = productPlaceholder1;
    }

    public String getProductPlaceholder2() {
        return productPlaceholder2;
    }

    public Product productPlaceholder2(String productPlaceholder2) {
        this.productPlaceholder2 = productPlaceholder2;
        return this;
    }

    public void setProductPlaceholder2(String productPlaceholder2) {
        this.productPlaceholder2 = productPlaceholder2;
    }

    public String getProductPlaceholder3() {
        return productPlaceholder3;
    }

    public Product productPlaceholder3(String productPlaceholder3) {
        this.productPlaceholder3 = productPlaceholder3;
        return this;
    }

    public void setProductPlaceholder3(String productPlaceholder3) {
        this.productPlaceholder3 = productPlaceholder3;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Product createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Product createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Product lastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<ProductCategory> getMyCategorys() {
        return myCategorys;
    }

    public Product myCategorys(Set<ProductCategory> productCategories) {
        this.myCategorys = productCategories;
        return this;
    }

    public Product addMyCategorys(ProductCategory productCategory) {
        myCategorys.add(productCategory);
        productCategory.getHasProducts().add(this);
        return this;
    }

    public Product removeMyCategorys(ProductCategory productCategory) {
        myCategorys.remove(productCategory);
        productCategory.getHasProducts().remove(this);
        return this;
    }

    public void setMyCategorys(Set<ProductCategory> productCategories) {
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
        Product product = (Product) o;
        if (product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
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
