package io.hreem.coffeeshop.monolith.ordering.domain.aggregates;


import io.hreem.coffeeshop.monolith.ordering.domain.entities.Customer;
import io.hreem.coffeeshop.monolith.ordering.domain.valueobjects.OrderDetails;
import io.hreem.coffeeshop.monolith.ordering.domain.valueobjects.OrderItem;
import io.hreem.coffeeshop.monolith.ordering.domain.valueobjects.OrderStatus;

import javax.persistence.*;
import java.util.List;

/**
 * Purpose:
 * Order aggregate, the central business object for the order bounded context.
 *
 * @author Hassan Nazar
 * @author www.hreem.io
 */
@Entity
public class OrderAggregate {

    // Surrogate ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Business ID
    private String orderId;

    private String ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> orderItems;

    public OrderAggregate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
