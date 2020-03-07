package net.hassannazar.outbox.domain;

import net.hassannazar.outbox.model.OutboxMessage;
import net.hassannazar.outbox.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OutboxingService {
    private static final Logger logger = LoggerFactory.getLogger(OutboxingService.class);

    @Inject
    private
    OutboxRepository repository;

    public long postToOutbox(final OutboxMessage message) {
        final var persistedMessage = this.repository.post(message);
        logger.info("Adding message to outbox of type: {}", message.getAggregateType());
        return persistedMessage.getId();
    }

    public void markAsSent(final OutboxMessage outboxMessage) {
        logger.info("Marking message with id {} as sent!", outboxMessage.getId());
        outboxMessage.setProcessedOn(LocalDateTime.now());
        this.repository.update(outboxMessage);
    }

    public List<OutboxMessage> pollRecords() {
        return this.repository.getUnprocessedMessages();
    }

}
