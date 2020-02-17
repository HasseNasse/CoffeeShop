package net.hassannazar.inventory.gateway;

import net.hassannazar.inventory.domain.InventoryService;
import net.hassannazar.inventory.domain.NotEnoughStockException;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderEventsSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventsSubscriber.class);

    @Inject
    InventoryService service;

    @Incoming("order-created")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> orderCreated(final Message<String> message) {
        logger.info("Received orderCreatedEvent = " + message.getPayload());
        final var order = OrderAggregate.fromJson(message.getPayload());
        try {
            return this.service.allocateBeans(order)
                    .thenRun(message::ack);
        } catch (final NotEnoughStockException e) {
            logger.error("Error", e);
            return CompletableFuture.failedFuture(null);
        }
    }
}
