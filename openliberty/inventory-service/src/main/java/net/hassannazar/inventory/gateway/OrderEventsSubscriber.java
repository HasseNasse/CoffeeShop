package net.hassannazar.inventory.gateway;

import net.hassannazar.application.AppContext;
import net.hassannazar.inventory.domain.InventoryService;
import net.hassannazar.inventory.model.OrderStatus;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import net.hassannazar.sludgebox.domain.SludgeBoxService;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Purpose:
 * Receives order events and processes the messaging by
 * passing to the inventory service for stock allocation.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class OrderEventsSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventsSubscriber.class);

    @Inject
    @AppContext
    ManagedExecutor managedExecutor;

    @Inject
    InventoryService service;

    @Inject
    SludgeBoxService sludgeBoxService;

    @Incoming("order-created")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> orderCreated(final Message<String> message) {
        try {
            final var order = OrderAggregate.fromJson(message.getPayload());
            // We are only interested in pending orders requiring bean-allocations
            if (order.status == OrderStatus.PENDING) {
                logger.info("Received orderCreatedEvent = " + message.getPayload());
                this.managedExecutor.supplyAsync(() -> this.service.allocateBeans(order)); // Attempt bean allocation
            }
        } catch (final Exception e) {
            logger.error("Error occurred during message parsing", e);
            this.managedExecutor.runAsync(() -> this.sludgeBoxService.handleUnreadable(message.getPayload()));
        }
        return message.ack(); // Ack and commit offsets
    }
}
