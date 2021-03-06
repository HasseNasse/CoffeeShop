package net.hassannazar.barista.gateway;

import net.hassannazar.barista.model.aggregate.OrderAggregate;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    @ConfigProperty(name = "mp.messaging.outgoing.order-processed.topic")
    String topic;

    @Inject
    @ConfigProperty(name = "mp.messaging.outgoing.order-processed.key.serializer")
    String keySerializer;

    @Inject
    @ConfigProperty(name = "mp.messaging.outgoing.order-processed.value.serializer")
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

    @Transactional
    public void publish(final OrderAggregate eventPayload) {
        // Publish a new kafka event
        this.producer.send(new ProducerRecord<>(this.topic, String.valueOf(eventPayload.orderId), eventPayload.toJson()));
    }

}
