package net.hassannazar.orders.gateway;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import net.hassannazar.application.exceptions.OrderPlacementException;
import net.hassannazar.orders.gateway.model.OrderCreated;
import net.hassannazar.orders.model.OrderEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@RequestScoped
public class OrderEventsProducer {

    @Inject
    @Channel("order-created")
    Emitter<OrderCreated> orderCreatedEvent;

    /**
     * Emits an order-created event.
     *
     * @param order coffee order
     */
    public void publishEvent(@Valid final OrderEntity order) {
        try {
            final OrderCreated orderCreated = new OrderCreated();
            orderCreated.id = order.id;
            orderCreated.quantity = order.quantity;
            orderCreated.type = order.type;
            // Send order-created event
            orderCreatedEvent.send(orderCreated);
        } catch (final Exception e) {
            throw new OrderPlacementException();
        }
    }
}
