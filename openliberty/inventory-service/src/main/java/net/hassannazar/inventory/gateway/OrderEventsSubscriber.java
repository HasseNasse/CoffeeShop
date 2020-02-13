package net.hassannazar.inventory.gateway;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import net.hassannazar.inventory.model.event.OrderCreatedEvent;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderEventsSubscriber {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventsSubscriber.class);

    @Incoming("order-created")
    @Acknowledgment(Acknowledgment.Strategy.MANUAL)
    public CompletionStage<Void> orderCreated(final KafkaMessage<String, String> message) {
        logger.debug(message.getPayload());
        final var orderCreatedEvent = OrderCreatedEvent.fromJson(message.getPayload());
        return message.ack();
    }
}
