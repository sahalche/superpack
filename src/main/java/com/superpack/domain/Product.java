package com.superpack.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private Double description;

    @Column(name = "name")
    private String name;

    @Column(name = "actual_rate")
    private Double actualRate;

    @Column(name = "offer_rate")
    private Double offerRate;

    @Column(name = "special_offer_item")
    private Boolean specialOfferItem;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImages = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDescription() {
        return description;
    }

    public Product description(Double description) {
        this.description = description;
        return this;
    }

    public void setDescription(Double description) {
        this.description = description;
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

    public Double getActualRate() {
        return actualRate;
    }

    public Product actualRate(Double actualRate) {
        this.actualRate = actualRate;
        return this;
    }

    public void setActualRate(Double actualRate) {
        this.actualRate = actualRate;
    }

    public Double getOfferRate() {
        return offerRate;
    }

    public Product offerRate(Double offerRate) {
        this.offerRate = offerRate;
        return this;
    }

    public void setOfferRate(Double offerRate) {
        this.offerRate = offerRate;
    }

    public Boolean isSpecialOfferItem() {
        return specialOfferItem;
    }

    public Product specialOfferItem(Boolean specialOfferItem) {
        this.specialOfferItem = specialOfferItem;
        return this;
    }

    public void setSpecialOfferItem(Boolean specialOfferItem) {
        this.specialOfferItem = specialOfferItem;
    }

    public Set<ProductImage> getProductImages() {
        return productImages;
    }

    public Product productImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
        return this;
    }

    public Product addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.setProduct(this);
        return this;
    }

    public Product removeProductImage(ProductImage productImage) {
        this.productImages.remove(productImage);
        productImage.setProduct(null);
        return this;
    }

    public void setProductImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Product reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Product addReview(Review review) {
        this.reviews.add(review);
        review.setProduct(this);
        return this;
    }

    public Product removeReview(Review review) {
        this.reviews.remove(review);
        review.setProduct(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Store getStore() {
        return store;
    }

    public Product store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", description=" + getDescription() +
            ", name='" + getName() + "'" +
            ", actualRate=" + getActualRate() +
            ", offerRate=" + getOfferRate() +
            ", specialOfferItem='" + isSpecialOfferItem() + "'" +
            "}";
    }
}
