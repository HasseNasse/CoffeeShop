package net.hassannazar.order.gateway;

import net.hassannazar.order.domain.OrderService;
import net.hassannazar.order.model.OrderStatus;
import net.hassannazar.order.model.event.InventoryAllocationEvent;
import net.hassannazar.order.model.event.StockEmptyEvent;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryEventsSubscriber {

    @Inject
    private
    OrderService orderService;

    @Incoming("beans-allocated")
    public void allocationEvent(final InventoryAllocationEvent payload) {
        this.orderService.updateStatus(payload.id, OrderStatus.PLACED);
    }

    @Incoming("beans-stock-empty")
    public void stockEmptyEvent(final StockEmptyEvent payload) {
        this.orderService.cancelOrder(payload.id);
    }
}
