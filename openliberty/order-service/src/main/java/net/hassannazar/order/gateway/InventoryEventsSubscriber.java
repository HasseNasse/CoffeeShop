package net.hassannazar.order.gateway;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InventoryEventsSubscriber {

    /*@Inject
    private
    OrderService orderService;

    @Incoming("beans-allocated")
    public void allocationEvent(final InventoryAllocationEvent payload) {
        this.orderService.updateStatus(payload.id, OrderStatus.PLACED);
    }

    @Incoming("beans-stock-empty")
    public void stockEmptyEvent(final StockEmptyEvent payload) {
        this.orderService.cancelOrder(payload.id);
    }*/
}
