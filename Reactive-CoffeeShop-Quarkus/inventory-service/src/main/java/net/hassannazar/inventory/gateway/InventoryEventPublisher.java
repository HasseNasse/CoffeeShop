package net.hassannazar.inventory.gateway;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class InventoryEventPublisher {

    @Inject
    @Channel("beans-allocated")
    Emitter<String> beansAllocatedEventEmitter;

    @Inject
    @Channel("beans-stock-empty")
    Emitter<String> beansStockEmptyEventEmitter;

    void notifyBeansAllocated(final long orderID) {
        beansAllocatedEventEmitter.send(Long.toString(orderID));
    }

    void notifyStockEmpty(final long orderID) {
        beansStockEmptyEventEmitter.send(Long.toString(orderID));
    }

}
