package net.hassannazar.inventory.repository;

import net.hassannazar.inventory.model.Inventory;
import net.hassannazar.inventory.model.ProductType;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class InventoryRepository {

    @PersistenceContext(unitName = "inventoryPU")
    EntityManager em;

    public Inventory getInventoryOfType(final ProductType coffeebeans) {
        final var query = this.em.createNamedQuery(Inventory.GET_BY_INVENTORY_TYPE, Inventory.class);
        query.setParameter("type", coffeebeans.toString());
        return query.getSingleResult();
    }

    public void updateInventory(final Inventory inventory) {
        this.em.merge(inventory);
    }
}
