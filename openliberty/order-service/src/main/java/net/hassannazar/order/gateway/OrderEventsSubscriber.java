package net.hassannazar.order.gateway;

import net.hassannazar.application.AppContext;
import net.hassannazar.order.domain.OrderService;
import net.hassannazar.order.model.aggregate.OrderAggregate;
import org.eclipse.microprofile.context.ManagedExecutor;
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
    private static final Logger logger = LoggerFactory.getLogger(OrderEventsSubscriber.class.getSimpleName());

    @Inject
    @AppContext
    ManagedExecutor managedExecutor;

    @Inject
    OrderService service;

    @Incoming("order-events")
    public CompletionStage<Void> onOrderEvent(final Message<String> message) {
        logger.info(message.getPayload());
        try {
            final var orderAggregate = OrderAggregate.fromJson(message.getPayload());
            return this.managedExecutor
                    .supplyAsync(() -> {
                        // Persist update
                        this.service.updateStatus(orderAggregate.orderId, orderAggregate.status);
                        return message.ack();
                    }).get();
        } catch (final Exception e) {
            logger.error("Failed to process message", e);
            return CompletableFuture.failedStage(e);
        }
    }


}
