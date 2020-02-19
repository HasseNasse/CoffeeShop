package net.hassannazar.order.domain;

import net.hassannazar.order.gateway.OrderEventsPublisher;
import net.hassannazar.order.model.OrderEntity;
import net.hassannazar.order.model.OrderStatus;
import net.hassannazar.order.model.aggregate.OrderAggregate;
import net.hassannazar.order.repository.OrderRepository;

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

        this.publisher.publish(order);
        return saved.getId();
    }

    public List<OrderEntity> getAllOrders() {
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
