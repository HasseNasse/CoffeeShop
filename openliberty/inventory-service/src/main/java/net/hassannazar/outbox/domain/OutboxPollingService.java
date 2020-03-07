package net.hassannazar.outbox.domain;

import net.hassannazar.inventory.gateway.OrderEventsPublisher;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import net.hassannazar.outbox.model.OutboxMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

/**
 * Purpose:
 * Polling service used when outbox pattern is used.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Startup
@Singleton
public class OutboxPollingService {

    private static final Logger logger = LoggerFactory.getLogger(OutboxPollingService.class.getSimpleName());

    @Inject
    @ConfigProperty(name = "application.use.transactional.outbox")
    boolean useOutbox;

    @Inject
    OutboxingService service;

    @Inject
    OrderEventsPublisher publisher;

    @Schedule(persistent = false, second = "*/5", minute = "*", hour = "*")
    public void atSchedule() {
        if (this.useOutbox) {
            logger.info("Outbox poll triggered");
            final var messageList = this.service.pollRecords();
            if (!messageList.isEmpty()) {
                postOutboxedMessages(messageList);
            } else {
                logger.info("No outbox messages found!");
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private void postOutboxedMessages(final List<OutboxMessage> messageList) {
        logger.info("Received outbox with size: {}", messageList.size());

        for (final OutboxMessage outboxMessage : messageList) {
            // Extract event payload
            final var payload = OrderAggregate.fromJson(outboxMessage.getAggregate());
            this.publisher.publish(payload);
            // Change status on the outbox message
            this.service.markAsSent(outboxMessage);
        }
    }

}
