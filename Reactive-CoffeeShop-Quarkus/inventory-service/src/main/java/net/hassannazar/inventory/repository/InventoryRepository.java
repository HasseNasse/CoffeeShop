package net.hassannazar.inventory.repository;

import net.hassannazar.application.exceptions.NotEnoughBeansException;
import net.hassannazar.inventory.model.Inventory;
import net.hassannazar.inventory.model.InventoryType;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class InventoryRepository {

    @Inject
    EntityManager em;

    public void allocate(final Inventory inventory, final int gramsToAllocate) throws NotEnoughBeansException {
        if (inventory.getStock() - gramsToAllocate < 0) {
            throw new NotEnoughBeansException();
        }

        // Allocate necessary beans for the order
        inventory.setStock(inventory.getStock() - gramsToAllocate);
        em.merge(inventory);
        System.out.println("Successfully allocated " + gramsToAllocate + " grams! " + inventory.getStock() + " left in stock!");
    }

    @Transactional
    public void restock(final Inventory inventory) {
        em.persist(inventory);
    }

    public Inventory getStock(final InventoryType type) {
        final var query = em.createNamedQuery("InventoryEntity.findByType", Inventory.class);
        query.setParameter("type", type);
        return query.getSingleResult();
    }
}
