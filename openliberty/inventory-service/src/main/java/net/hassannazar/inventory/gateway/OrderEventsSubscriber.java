package net.hassannazar.inventory.gateway;

import net.hassannazar.application.AppContext;
import net.hassannazar.inventory.domain.InventoryService;
import net.hassannazar.inventory.domain.NotEnoughStockException;
import net.hassannazar.inventory.model.OrderStatus;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import net.hassannazar.sludgebox.domain.SludgeBoxService;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbException;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

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
    SludgeBoxService sludgeBox;

    @Incoming("order-created")
    public CompletionStage<Void> orderCreated(final Message<String> message) {
        try {
            final var order = OrderAggregate.fromJson(message.getPayload());
            // We are only interested in pending orders requiring bean-allocations
            if (order.status == OrderStatus.PENDING) {
                logger.info("Received orderCreatedEvent = {}", message.getPayload());
                this.managedExecutor.runAsync(() -> this.service.allocateBeans(order))
                        .exceptionally(handleBeanAllocationError(order));
            }
        } catch (final JsonbException e) {
            logger.error("Error occurred during message parsing", e);
            this.managedExecutor.runAsync(() -> this.sludgeBox.handleUnreadable(message.getPayload()));
        }
        return message.ack(); // Ack and commit offsets
    }

    private Function<Throwable, Void> handleBeanAllocationError(final OrderAggregate order) {
        return t -> {
            if (t instanceof NotEnoughStockException) {
                this.service.notifyStockEmpty(order);
            }
            return null;
        };
    }
}
