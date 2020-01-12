package net.hassannazar.orders.gateway;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import net.hassannazar.orders.model.Order;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class OrderStatusProducer {


    @Inject
    @Channel("order-status")
    Emitter<Order> orderStatusEmitter;


    public void send (Order order) {
        orderStatusEmitter.send(order);
    }
}
