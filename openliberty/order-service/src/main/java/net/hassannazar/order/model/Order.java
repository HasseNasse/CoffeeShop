package net.hassannazar.order.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Entity
@Table(name = "orders")
@NamedQuery(name = "Orders.getAll", query = "select d from Orders d")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private final LocalDateTime orderDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoffeeType type;

    @NotNull
    @Min(1)
    @Max(5)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order() {
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.PENDING;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    public CoffeeType getType() {
        return this.type;
    }

    public void setType(final CoffeeType type) {
        this.type = type;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(final OrderStatus status) {
        this.orderStatus = status;
    }
}
