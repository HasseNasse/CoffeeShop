package net.hassannazar.inventory.domain;

import net.hassannazar.inventory.model.Inventory;
import net.hassannazar.inventory.model.ProductType;
import net.hassannazar.inventory.model.aggregate.OrderAggregate;
import net.hassannazar.inventory.repository.InventoryRepository;
import org.eclipse.microprofile.faulttolerance.Asynchronous;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class InventoryService {

    @Inject
    InventoryRepository repository;

    @Inject
    CoffeeBeansResolver beansResolver;

    @Asynchronous
    @Transactional
    public CompletionStage<Inventory> allocateBeans(final OrderAggregate order) throws NotEnoughStockException {
        final var beansRequired = this.beansResolver.resolve(order.coffeeType);
        final var inventory = this.repository.getInventoryOfType(ProductType.COFFEEBEANS);
        // If we don't have enough beans, throw
        if (inventory.getQuantity() - beansRequired < 0) {
            throw new NotEnoughStockException("Not enough beans to handle order!");
        }
        inventory.setQuantity(inventory.getQuantity() - beansRequired);
        this.repository.updateInventory(inventory);
        return CompletableFuture.completedFuture(inventory);
    }
}
