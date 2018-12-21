package com.ktran.shopping_cart.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class OrderProduct {

    @EmbeddedId
    @JsonIgnore
    private OrderProductPK orderProductPk;

    @Column(nullable = false) private Integer quantity;

    public OrderProduct() {
        super();
    }

    public OrderProduct(Order order, Product product, Integer quantity) {
        orderProductPk = new OrderProductPK();
        orderProductPk.setOrder(order);
        orderProductPk.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.orderProductPk.getProduct();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

    public OrderProductPK getOrderProductPk() {
        return orderProductPk;
    }

    public void setOrderProductPk(OrderProductPK pk) {
        this.orderProductPk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderProductPk == null) ? 0 : orderProductPk.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderProduct other = (OrderProduct) obj;
        if (orderProductPk == null) {
            if (other.orderProductPk != null) {
                return false;
            }
        } else if (!orderProductPk.equals(other.orderProductPk)) {
            return false;
        }

        return true;
    }


}
