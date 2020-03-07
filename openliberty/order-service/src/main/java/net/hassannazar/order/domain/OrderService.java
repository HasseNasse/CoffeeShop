package net.hassannazar.order.domain;

import net.hassannazar.order.gateway.OrderEventsPublisher;
import net.hassannazar.order.model.OrderEntity;
import net.hassannazar.order.model.OrderStatus;
import net.hassannazar.order.model.aggregate.OrderAggregate;
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
    private OrderEventsPublisher publisher;

    @Inject
    @ConfigProperty(name = "application.use.transactional.outbox")
    boolean useTransactionalOutbox;

    @Inject
    OutboxingService outboxingService;

    @Transactional
    public long createOrder(final OrderAggregate order) {
        // Persist order
        final var entity = new OrderEntity();
        entity.setOrderer(order.orderer);
        entity.setType(order.coffeeType);
        entity.setQuantity(order.quantity);
        final var saved = this.repository.save(entity);

        // Refresh order with persisted data
        order.orderId = saved.getId();
        order.status = saved.getOrderStatus();

        /// A Transactional outbox will handle publishing of messages to
        /// a broker by reading an outbox of messages that are posted as
        /// an atomic operation during order creation.
        if (this.useTransactionalOutbox) {
            // Post outbox message
            final var outBoxMessage = new OutboxMessage();
            outBoxMessage.setAggregate(order.toJson());
            outBoxMessage.setAggregateType(OrderAggregate.class.getSimpleName());
            this.outboxingService.postToOutbox(outBoxMessage);
        } else {
            this.publisher.publish(order);
        }
        return saved.getId();
    }

    public List<OrderEntity> getAllOrders() {
        return this.repository.getAllOrders();
    }

    @Transactional
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
