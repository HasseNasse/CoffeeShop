package io.hreem.coffeeshop.monolith.ordering.controller.internal.commands;

import io.hreem.coffeeshop.monolith.ordering.domain.readobjects.Order;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderCommands {

    public long createOrder(final Order order) {
        return -1L;
    }

}
