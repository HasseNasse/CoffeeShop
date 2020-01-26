package net.hassannazar.inventory.gateway;

import net.hassannazar.application.exceptions.NotEnoughBeansException;
import net.hassannazar.inventory.domain.BeanAllocatorService;
import net.hassannazar.inventory.gateway.model.OrderCreated;
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
public class OrderEventsSubscriber {

    @Inject
    @ConfigProperty(name = "application.name")
    String whoAmI;

    @Inject
    BeanAllocatorService beanAllocator;

    @Inject
    InventoryEventPublisher eventPublisher;

    @Incoming("order-created")
    public void orderPlacedEmission(final OrderCreated orderCreated) {
        try {
            beanAllocator.allocateBeansForOrder(orderCreated);
            // Everything went well, send notification that beans are allocated
            eventPublisher.notifyBeansAllocated(orderCreated.id);
        } catch (final NotEnoughBeansException e) {
            eventPublisher.notifyStockEmpty(orderCreated.id);
        }
    }
}
