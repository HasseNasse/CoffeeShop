package net.hassannazar.order.gateway;

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

    private KafkaProducer<String, String> producer;

    @PostConstruct
    public void init() {
        final var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.servers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        this.producer = new KafkaProducer<>(properties);
    }

    public void publish(final String eventPayload) {
        this.producer.send(new ProducerRecord<>(this.topic, eventPayload));
    }

}
