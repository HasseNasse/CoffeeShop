package net.hassannazar.orders.domain;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import net.hassannazar.application.exceptions.OrderPlacementException;
import net.hassannazar.orders.model.Order;
import net.hassannazar.orders.model.OrderStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@RequestScoped
public class OrderService {

    @Inject
    @ConfigProperty(name = "application.name")
    String whoAmI;

    @Inject
    @Channel("orders")
    Emitter<Order> orderEmitter;

    /**
     * Emits an order-received event.
     *
     * @param order coffee order
     */
    public void placeOrder (@Valid Order order) {
        try {
            order.id = UUID.randomUUID();
            order.status = OrderStatus.PLACED;
            order.created = LocalDateTime.now();
            order.modified = LocalDateTime.now();
            order.modifier = whoAmI;

            orderEmitter.send(order);
        } catch (Exception e){
            throw new OrderPlacementException();
        }
    }
}
