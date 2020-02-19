package net.hassannazar.barista.gateway;

import net.hassannazar.application.AppContext;
import net.hassannazar.barista.domain.BaristaService;
import net.hassannazar.barista.model.OrderStatus;
import net.hassannazar.barista.model.aggregate.OrderAggregate;
import net.hassannazar.sludgebox.domain.SludgeBoxService;
import org.eclipse.microprofile.context.ManagedExecutor;
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
 * passing to the barista service for order finalization.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class OrderEventsSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventsSubscriber.class);

    @Inject
    SludgeBoxService sludgeBoxService;

    @Inject
    BaristaService baristaService;

    @Inject
    @AppContext
    ManagedExecutor managedExecutor;

    @Incoming("order-inventory-allocated")
    public CompletionStage<Void> onOrderAllocated(final Message<String> message) {
        try {
            final var order = OrderAggregate.fromJson(message.getPayload());
            if (order.status == OrderStatus.PLACED) {
                this.managedExecutor.runAsync(() -> this.baristaService.prepareCoffee(order));
            }
        } catch (final Exception e) {
            logger.error("Failed to handle incoming message", e);
            this.managedExecutor.runAsync(() -> this.sludgeBoxService.handleUnreadable(message.getPayload()));
        }
        return message.ack();
    }

}
