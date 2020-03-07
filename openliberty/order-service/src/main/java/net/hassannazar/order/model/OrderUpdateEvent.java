package net.hassannazar.order.model;

import net.hassannazar.order.model.aggregate.OrderAggregate;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderUpdateEvent {

    private final OrderAggregate eventAggregate;

    public OrderUpdateEvent(final OrderAggregate eventAggregate) {
        this.eventAggregate = eventAggregate;
    }

    public OrderAggregate getEventPayload() {
        return this.eventAggregate;
    }
}
