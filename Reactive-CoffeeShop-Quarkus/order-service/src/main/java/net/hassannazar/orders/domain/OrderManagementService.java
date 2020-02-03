package net.hassannazar.orders.domain;

import net.hassannazar.application.exceptions.NoOrdersFoundException;
import net.hassannazar.orders.gateway.OrderEventsProducer;
import net.hassannazar.orders.model.OrderEntity;
import net.hassannazar.orders.model.OrderStatus;
import net.hassannazar.orders.model.read.Order;
import net.hassannazar.orders.repository.OrdersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@RequestScoped
public class OrderManagementService {

    @Inject
    OrdersRepository repository;

    @Inject
    OrderEventsProducer eventsProducer;

    public long placeOrder(final Order order) {
        final OrderEntity entity = new OrderEntity();
        entity.orderDate = LocalDateTime.now();
        entity.quantity = order.getQuantity();
        entity.status = OrderStatus.PENDING;
        entity.type = order.getType();
        final var savedOrder = repository.save(entity);
        eventsProducer.publishEvent(savedOrder);
        return savedOrder.id;
    }

    public List<Order> getAllOrders() {
        final List<OrderEntity> orderList = repository.getAll();
        if (orderList.isEmpty()) {
            throw new NoOrdersFoundException();
        }
        return orderList.stream()
                .map(OrderEntity::toReadObject)
                .collect(Collectors.toList());
    }
}
