package net.hassannazar.orders.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import net.hassannazar.orders.model.read.Order;

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
public class OrderEntity extends PanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime orderDate;
    private String type;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int quantity;

    public OrderEntity() {
    }

    public static Order toReadObject(final OrderEntity orderEntity) {
        return new Order.Builder()
                .setId(orderEntity.id)
                .setOrderDate(orderEntity.orderDate)
                .setQuantity(orderEntity.quantity)
                .setStatus(orderEntity.status)
                .setType(orderEntity.type)
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(final OrderStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}
