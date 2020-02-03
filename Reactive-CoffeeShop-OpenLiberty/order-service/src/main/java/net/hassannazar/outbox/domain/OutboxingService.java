package net.hassannazar.outbox.domain;

import net.hassannazar.outbox.model.OutboxMessage;
import net.hassannazar.outbox.repository.OutboxRepository;

import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OutboxingService {

    @Inject
    private
    OutboxRepository repository;

    public long postToOutbox(final OutboxMessage message) {
        final var persistedMessage = this.repository.post(message);
        return persistedMessage.getId();
    }

}
