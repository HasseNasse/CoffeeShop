package net.hassannazar.outbox.repository;

import net.hassannazar.outbox.model.OutboxMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Purpose:
 * Handles outbox message persistence
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OutboxRepository {

    @PersistenceContext(unitName = "ordersPU")
    private EntityManager em;

    @Transactional
    public OutboxMessage post(final OutboxMessage message) {
        this.em.persist(message);
        this.em.flush();
        return message;
    }

    public List<OutboxMessage> getUnprocessedMessages() {
        final var namedQuery = this.em.createNamedQuery(OutboxMessage.FIND_ALL_UNPROCESSED, OutboxMessage.class);
        return namedQuery.getResultList();
    }

    public void update(final OutboxMessage message) {
        this.em.merge(message);
    }
}
