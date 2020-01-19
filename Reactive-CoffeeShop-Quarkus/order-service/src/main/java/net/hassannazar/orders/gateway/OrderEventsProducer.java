package net.hassannazar.orders.gateway;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import net.hassannazar.application.exceptions.OrderPlacementException;
import net.hassannazar.orders.model.read.Order;

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
    @Channel("order-received")
    Emitter<Order> orderEventEmitter;

    /**
     * Emits an order-received event.
     *
     * @param order coffee order
     */
    public void publishEvent(@Valid final Order order) {
        try {
            // Send order-received event
            orderEventEmitter.send(order);
        } catch (final Exception e) {
            throw new OrderPlacementException();
        }
    }
}
