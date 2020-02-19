package net.hassannazar.outbox.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Entity
@Table(name = "eventOutbox")
@NamedQuery(name = OutboxMessage.FIND_ALL_UNPROCESSED, query = "select c from OutboxMessage c where c.processedOn is null")
public class OutboxMessage {
    public static final String FIND_ALL_UNPROCESSED = "OutboxMessage.getUnprocessed";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String aggregateType;
    private String aggregate;
    private LocalDateTime occurredOn;
    private LocalDateTime processedOn;

    public OutboxMessage() {
        this.occurredOn = LocalDateTime.now();
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getAggregateType() {
        return this.aggregateType;
    }

    public void setAggregateType(final String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getAggregate() {
        return this.aggregate;
    }

    public void setAggregate(final String aggregate) {
        this.aggregate = aggregate;
    }

    public LocalDateTime getOccurredOn() {
        return this.occurredOn;
    }

    public void setOccurredOn(final LocalDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public LocalDateTime getProcessedOn() {
        return this.processedOn;
    }

    public void setProcessedOn(final LocalDateTime processedOn) {
        this.processedOn = processedOn;
    }
}
