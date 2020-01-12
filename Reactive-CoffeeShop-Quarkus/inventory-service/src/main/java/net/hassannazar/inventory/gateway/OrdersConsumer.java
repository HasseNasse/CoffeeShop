package net.hassannazar.inventory.gateway;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import net.hassannazar.application.exceptions.NoSuchCoffeeException;
import net.hassannazar.application.exceptions.NotEnoughBeansException;
import net.hassannazar.inventory.domain.BeanAllocatorService;
import net.hassannazar.inventory.domain.CoffeeStatusNotifier;
import net.hassannazar.inventory.model.Order;
import net.hassannazar.inventory.model.OrderStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class OrdersConsumer {

    @Inject
    @ConfigProperty(name = "application.name")
    String whoAmI;

    @Inject
    CoffeeStatusNotifier notifier;

    @Inject
    BeanAllocatorService beanAllocator;

    @Inject
    @Channel("beans-allocated")
    Emitter<String> beansAllocatedEventEmitter;

    @Incoming("order-received")
    public void orderPlacedEmission (Order order) {
        try {
            beanAllocator.allocateBeansForOrder(order);
            // Everything went well, send notification that beans are allocated
            beansAllocatedEventEmitter.send(order.id.toString());
            // Also set new status event for the order
            order.status = OrderStatus.BEANS_ALLOCATED;
            notifier.sendStatusNotification(order);
        } catch (NoSuchCoffeeException | NotEnoughBeansException e) {
            order.status = OrderStatus.ORDER_FAILED;
            order.modifier = whoAmI;
            notifier.sendStatusNotification(order);
        }
    }
}
