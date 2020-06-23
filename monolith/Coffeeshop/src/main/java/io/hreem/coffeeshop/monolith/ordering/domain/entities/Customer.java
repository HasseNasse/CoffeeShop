package io.hreem.coffeeshop.monolith.ordering.domain.entities;

import io.hreem.coffeeshop.monolith.ordering.domain.aggregates.OrderAggregate;

import javax.persistence.*;
import java.util.List;


@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
    private String name;
    @OneToMany
    @JoinColumn(name = "orderId")
    private List<OrderAggregate> orderAggregate;

    public Customer() {
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
