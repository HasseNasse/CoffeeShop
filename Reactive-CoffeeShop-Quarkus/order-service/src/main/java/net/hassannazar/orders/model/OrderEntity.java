package net.hassannazar.orders.model;

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
@NamedQuery(name = "OrderEntity.findAll", query = "select c from OrderEntity c")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public LocalDateTime orderDate;
    public String type;
    @Enumerated(EnumType.STRING)
    public OrderStatus status;
    public int quantity;

    public static Order toReadObject(final OrderEntity orderEntity) {
        return new Order.Builder()
                .setId(orderEntity.id)
                .setOrderDate(orderEntity.orderDate)
                .setQuantity(orderEntity.quantity)
                .setStatus(orderEntity.status)
                .setType(orderEntity.type)
                .build();
    }
}
