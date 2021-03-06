package net.hassannazar.inventory.domain;

import net.hassannazar.inventory.gateway.OrderEventsPublisher;
import net.hassannazar.inventory.model.OrderStatus;
import net.hassannazar.inventory.model.ProductType;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import net.hassannazar.inventory.repository.InventoryRepository;
import net.hassannazar.outbox.domain.OutboxingService;
import net.hassannazar.outbox.model.OutboxMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Inject
    @ConfigProperty(name = "application.use.transactional.outbox")
    boolean useTransactionalOutbox;

    @Inject
    InventoryRepository repository;

    @Inject
    CoffeeBeansResolver beansResolver;

    @Inject
    OrderEventsPublisher eventsPublisher;

    @Inject
    OutboxingService outboxingService;

    @Transactional
    public void allocateBeans(final OrderAggregate order) {
        logger.info("Allocating beans for order with id: {} for orderer: {}", order.orderId, order.orderer);
        final var beansRequired = this.beansResolver.resolve(order.coffeeType);
        final var inventory = this.repository.getInventoryOfType(ProductType.COFFEEBEANS);
        // If we don't have enough beans, throw
        final var stockAfterOrderCompletion = inventory.getQuantity() - beansRequired * order.quantity;
        if (stockAfterOrderCompletion < 0) {
            logger.error("Allocation failed for order id: {}. Not enough beans in stock {}", order.orderId, stockAfterOrderCompletion);
            throw new NotEnoughStockException("Not enough beans to handle order!");
        }
        logger.info("Allocating {} beans for order with id {}", beansRequired, order.orderId);
        inventory.setQuantity(stockAfterOrderCompletion);
        this.repository.updateInventory(inventory);

        // Update status of order for emission
        order.status = OrderStatus.PLACED;
        if (this.useTransactionalOutbox) {
            final var outboxMessage = new OutboxMessage();
            outboxMessage.setAggregateType(OrderAggregate.class.getSimpleName());
            outboxMessage.setAggregate(order.toJson());
            this.outboxingService.postToOutbox(outboxMessage);
        } else {
            this.eventsPublisher.publish(order);
        }
    }

    @Transactional
    public void notifyStockEmpty(final OrderAggregate order) {
        order.status = OrderStatus.BEANS_OUT_OF_STOCK;

        if (this.useTransactionalOutbox) {
            final var outboxMessage = new OutboxMessage();
            outboxMessage.setAggregateType(OrderAggregate.class.getSimpleName());
            outboxMessage.setAggregate(order.toJson());
            this.outboxingService.postToOutbox(outboxMessage);
        } else {
            this.eventsPublisher.publish(order);
        }
    }

}
