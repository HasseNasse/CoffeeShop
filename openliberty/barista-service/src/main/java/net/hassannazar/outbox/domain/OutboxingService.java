package net.hassannazar.outbox.domain;

import net.hassannazar.outbox.model.OutboxMessage;
import net.hassannazar.outbox.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

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
        logger.info("Adding message to outbox of type: {}", message.getAggregateType());
        final var persistedMessage = this.repository.post(message);
        return persistedMessage.getId();
    }

}
