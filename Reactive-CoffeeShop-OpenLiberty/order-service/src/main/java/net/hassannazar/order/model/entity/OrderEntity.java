package net.hassannazar.order.model.entity;

import net.hassannazar.order.model.CoffeeType;
import net.hassannazar.order.model.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private CoffeeType type;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderEntity() {
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.PENDING;
    }

    public long getId() {
        return this.id;
    }

    public OrderEntity setId(final long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    public OrderEntity setOrderDate(final LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public CoffeeType getType() {
        return this.type;
    }

    public OrderEntity setType(final CoffeeType type) {
        this.type = type;
        return this;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public OrderEntity setQuantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public OrderEntity setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }
}
