package net.hassannazar.inventory.gateway;

import net.hassannazar.application.AppContext;
import net.hassannazar.inventory.domain.InventoryService;
import net.hassannazar.inventory.model.OrderStatus;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbException;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderEventsSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventsSubscriber.class);

    @Inject
    @AppContext
    ManagedExecutor managedExecutor;

    @Inject
    InventoryService service;

    @Incoming("order-created")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> orderCreated(final Message<String> message) {
        try {
            final var order = OrderAggregate.fromJson(message.getPayload());
            // We are only interested in pending orders requiring bean-allocations
            if (order.status != OrderStatus.PENDING) {
                logger.info("Ignoring received order-event = " + message.getPayload());
                return message.ack();
            }
            logger.info("Received orderCreatedEvent = " + message.getPayload());
            return this.managedExecutor
                    .supplyAsync(() -> this.service.allocateBeans(order)) // Attempt bean allocation
                    .thenRun(message::ack); // Acknowledge and Commit topic offsets
        } catch (final JsonbException e) {
            return message.ack(); // Message is corrupt, ack and move on.
        }
    }
}
