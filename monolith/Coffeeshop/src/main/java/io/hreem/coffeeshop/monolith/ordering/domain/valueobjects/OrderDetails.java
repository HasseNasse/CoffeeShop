package io.hreem.coffeeshop.monolith.ordering.domain.valueobjects;

import javax.persistence.Embeddable;
import java.util.List;


@Embeddable
public class OrderDetails {
    private String ticketId;
    private List<OrderItem> orderItems;

    public OrderDetails() {
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
