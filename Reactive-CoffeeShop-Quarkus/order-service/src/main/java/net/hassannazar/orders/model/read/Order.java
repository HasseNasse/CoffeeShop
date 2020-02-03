package net.hassannazar.orders.model.read;

import net.hassannazar.orders.model.OrderStatus;
import org.wildfly.common.annotation.NotNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class Order implements Serializable {

    public long id;
    public LocalDateTime orderDate;
    @NotEmpty(message = "Coffee type name must be present")
    public String type;
    @NotNull
    public int quantity;
    public OrderStatus status;

    public Order() {
    }

    private Order(final Builder that) {
        id = that.id;
        orderDate = that.orderDate;
        type = that.type;
        quantity = that.quantity;
        status = that.status;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public static class Builder {
        private long id;
        private LocalDateTime orderDate;
        private String type;
        private int quantity;
        private OrderStatus status;

        public Builder setId(final long id) {
            this.id = id;
            return this;
        }

        public Builder setOrderDate(final LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setType(final String type) {
            this.type = type;
            return this;
        }

        public Builder setQuantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setStatus(final OrderStatus status) {
            this.status = status;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
