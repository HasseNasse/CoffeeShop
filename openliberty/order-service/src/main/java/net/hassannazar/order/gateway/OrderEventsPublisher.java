package net.hassannazar.order.gateway;

import net.hassannazar.order.model.aggregate.OrderAggregate;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;

/**
 * Purpose:
 * Sends out events to kafka broker or a transactional outbox
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class OrderEventsPublisher {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventsPublisher.class.getSimpleName());

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
        logger.info("Sending order-created event with key: {}", eventPayload.orderId);
        this.producer.send(new ProducerRecord<>(this.topic, String.valueOf(eventPayload.orderId), eventPayload.toJson()));
    }

}
