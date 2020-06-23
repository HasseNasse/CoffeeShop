package io.hreem.coffeeshop.monolith.ordering.domain.aggregates;

import javax.persistence.Embeddable;


@Embeddable
public class OrderId {
    private String orderId;

    public OrderId() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
