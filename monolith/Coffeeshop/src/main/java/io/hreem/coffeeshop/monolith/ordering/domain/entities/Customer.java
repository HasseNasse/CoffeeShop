package io.hreem.coffeeshop.monolith.ordering.domain.entities;

import io.hreem.coffeeshop.monolith.ordering.domain.aggregates.OrderAggregate;

import javax.persistence.*;
import java.util.List;


@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long customerId;
    private String name;
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
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
