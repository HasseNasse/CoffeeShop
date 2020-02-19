package net.hassannazar.order.gateway;

import net.hassannazar.order.model.aggregate.OrderAggregate;
import net.hassannazar.outbox.domain.OutboxingService;
import net.hassannazar.outbox.model.OutboxMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Properties;

/**
 * Purpose:
 * Sends out events to kafka broker or a transactional outbox
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class OrderEventsPublisher {

    @Inject
    @ConfigProperty(name = "mp.messaging.connector.liberty-kafka.bootstrap.servers")
    String servers;

    @Inject
    @ConfigProperty(name = "mp.messaging.outgoing.order-created.topic")
    String topic;

    @Inject
    @ConfigProperty(name = "mp.messaging.outgoing.order-created.key.serializer")
    String keySerializer;

    @Inject
    @ConfigProperty(name = "mp.messaging.outgoing.order-created.value.serializer")
    String valueSerializer;

    @Inject
    @ConfigProperty(name = "application.use.transactional.outbox")
    boolean useTransactionalOutbox;

    @Inject
    OutboxingService outboxingService;

    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void init() {
        final var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.valueSerializer);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");

        this.producer = new KafkaProducer<>(properties);
    }

    public void publish(final OrderAggregate eventPayload) {
        /// A Transactional outbox will handle publishing of messages to
        /// a broker by reading an outbox of messages that are posted as
        /// an atomic operation during order creation.
        if (this.useTransactionalOutbox) {
            // Post outbox message
            final var outBoxMessage = new OutboxMessage();
            outBoxMessage.setAggregate(eventPayload.toJson());
            outBoxMessage.setAggregateType(OrderAggregate.class.getSimpleName());
            this.outboxingService.postToOutbox(outBoxMessage);
        } else {
            this.producer.send(new ProducerRecord<>(this.topic, eventPayload.toJson()));
        }
    }

}
