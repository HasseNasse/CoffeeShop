package io.hreem.coffeeshop.monolith.ordering.domain.valueobjects;

import io.hreem.coffeeshop.monolith.ordering.domain.aggregates.OrderAggregate;

import javax.persistence.*;


@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderAggregate orderAggregate;

    private String itemCode;
    private int quantity;
    private String detail;

    public OrderItem() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
