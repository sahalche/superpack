package com.superpack.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrderList.
 */
@Entity
@Table(name = "order_list")
public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "user_idp_code")
    private String userIdpCode;

    @Column(name = "payment_done")
    private Boolean paymentDone;

    @OneToMany(mappedBy = "orderList")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public OrderList totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public OrderList createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserIdpCode() {
        return userIdpCode;
    }

    public OrderList userIdpCode(String userIdpCode) {
        this.userIdpCode = userIdpCode;
        return this;
    }

    public void setUserIdpCode(String userIdpCode) {
        this.userIdpCode = userIdpCode;
    }

    public Boolean isPaymentDone() {
        return paymentDone;
    }

    public OrderList paymentDone(Boolean paymentDone) {
        this.paymentDone = paymentDone;
        return this;
    }

    public void setPaymentDone(Boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public OrderList orderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
        return this;
    }

    public OrderList addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrderList(this);
        return this;
    }

    public OrderList removeOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.remove(orderProduct);
        orderProduct.setOrderList(null);
        return this;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderList)) {
            return false;
        }
        return id != null && id.equals(((OrderList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderList{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", userIdpCode='" + getUserIdpCode() + "'" +
            ", paymentDone='" + isPaymentDone() + "'" +
            "}";
    }
}
