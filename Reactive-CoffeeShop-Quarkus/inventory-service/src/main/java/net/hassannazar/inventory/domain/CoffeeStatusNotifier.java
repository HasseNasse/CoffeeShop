package net.hassannazar.inventory.domain;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import net.hassannazar.inventory.model.Order;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class CoffeeStatusNotifier {

    @Inject
    @Channel("order-status")
    Emitter<Order> orderStatusEmitter;

    public void sendStatusNotification (Order order) {
        orderStatusEmitter.send(order);
    }
}
