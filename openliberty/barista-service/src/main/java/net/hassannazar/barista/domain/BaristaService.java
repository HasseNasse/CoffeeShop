package net.hassannazar.barista.domain;

import net.hassannazar.barista.gateway.OrderEventsPublisher;
import net.hassannazar.barista.model.OrderStatus;
import net.hassannazar.barista.model.aggregate.OrderAggregate;
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
public class BaristaService {
    private static final Logger logger = LoggerFactory.getLogger(BaristaService.class);

    @Inject
    OrderEventsPublisher eventsPublisher;

    @Transactional
    public void prepareCoffee(final OrderAggregate orderAggregate) {
        logger.info("Preparing order of {} and quantity {} for orderer: {}", orderAggregate.coffeeType, orderAggregate.quantity, orderAggregate.orderer);
        try {
            logger.info("Starting order of {} and quantity {} for orderer: {}", orderAggregate.coffeeType, orderAggregate.quantity, orderAggregate.orderer);
            Thread.sleep(5000); // Simulate complex business logic
            logger.info("Finishing order of {} and quantity {} for orderer: {}", orderAggregate.coffeeType, orderAggregate.quantity, orderAggregate.orderer);
            orderAggregate.status = OrderStatus.FINISHED;

            // Publish an event when order is finished
            this.eventsPublisher.publish(orderAggregate);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

}
