package net.hassannazar.order.domain;

import net.hassannazar.order.gateway.OrderEventsPublisher;
import net.hassannazar.order.model.OrderStatus;
import net.hassannazar.order.model.Orders;
import net.hassannazar.order.model.event.OrderCreatedEvent;
import net.hassannazar.order.repository.OrderRepository;
import net.hassannazar.outbox.domain.OutboxingService;
import net.hassannazar.outbox.model.OutboxMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class OrderService {

    @Inject
    private
    OrderRepository repository;

    @Inject
    private
    OutboxingService outboxingService;

    @Inject
    private OrderEventsPublisher publisher;

    @Inject
    @ConfigProperty(name = "application.use.transactional.outbox")
    private boolean useTransactionalOutbox;

    @Transactional
    public long createOrder(final Orders entity) {
        // Persist order
        final var saved = this.repository.save(entity);

        final var event = new OrderCreatedEvent();
        event.coffeeType = saved.getType();
        event.quantity = saved.getQuantity();
        event.orderId = saved.getId();
        // Post outbox message
        final var outBoxMessage = new OutboxMessage();
        outBoxMessage.setAggregate(event.toJson());
        outBoxMessage.setAggregateType(OrderCreatedEvent.class.getSimpleName());

        /// A Transactional outbox will handle publishing of messages to
        /// a broker by reading an outbox of messages that are posted as
        /// an atomic operation during order creation.
        if (this.useTransactionalOutbox) {
            this.outboxingService.postToOutbox(outBoxMessage);
        } else {
            this.publisher.publish(event.toJson());
        }

        return saved.getId();
    }

    public List<Orders> getAllOrders() {
        return this.repository.getAllOrders();
    }

    public void updateStatus(final long id, final OrderStatus status) {
        try {
            final var order = this.repository.findById(id);
            order.setOrderStatus(status);
            this.repository.update(order);
        } catch (final Exception e) {
            cancelOrder(id);
        }
    }

    public void cancelOrder(final long id) {
        final var order = this.repository.findById(id);
        order.setOrderStatus(OrderStatus.CANCELLED);
        this.repository.update(order);
    }
}
