package net.hassannazar.inventory.domain;

import net.hassannazar.application.exceptions.NotEnoughBeansException;
import net.hassannazar.inventory.gateway.model.OrderCreated;
import net.hassannazar.inventory.model.Inventory;
import net.hassannazar.inventory.model.InventoryType;
import net.hassannazar.inventory.repository.InventoryRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class BeanAllocatorService {

    @Inject
    InventoryRepository inventoryRepository;

    @Inject
    CoffeeClassifier classifier;

    public void restockBeansInInventory(final int beansStock) {
        final var inventory = new Inventory();
        inventory.setStock(beansStock);
        inventory.setType(InventoryType.BEANS);
        inventoryRepository.restock(inventory);
    }

    @Transactional
    public CompletionStage<Void> allocateBeansForOrder(@Valid final OrderCreated order) throws NotEnoughBeansException {
        System.out.println("Attempting to allocate beans for order with id: " + order.id);
        final int gramsOfBeans = classifier.classifyCoffee(order.type);
        final var inventory = inventoryRepository.getStock(InventoryType.BEANS);
        inventoryRepository.allocate(inventory, gramsOfBeans * order.quantity);
        return CompletableFuture.completedFuture(null);
    }
}
