package net.hassannazar.orders.domain;

import net.hassannazar.orders.gateway.OrderEventsProducer;
import net.hassannazar.orders.model.OrderEntity;
import net.hassannazar.orders.model.OrderStatus;
import net.hassannazar.orders.model.read.Order;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    OrderEventsProducer eventsProducer;

    @Transactional
    public void placeOrder(final Order order) {
        final OrderEntity entity = new OrderEntity();
        entity.setOrderDate(LocalDateTime.now());
        entity.setQuantity(order.getQuantity());
        entity.setStatus(OrderStatus.PENDING);
        entity.setType(order.getType());
        entity.persist();
        eventsProducer.publishEvent(order);
    }

    public List<Order> getAllOrders() {
        final List<OrderEntity> orderList = OrderEntity.listAll();
        return orderList.stream()
                .map(OrderEntity::toReadObject)
                .collect(Collectors.toList());
    }
}
