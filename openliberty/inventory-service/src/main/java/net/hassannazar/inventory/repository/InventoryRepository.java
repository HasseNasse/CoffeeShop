package net.hassannazar.inventory.repository;

import net.hassannazar.inventory.model.Inventory;
import net.hassannazar.inventory.model.ProductType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class InventoryRepository {

    @PersistenceContext(unitName = "inventoryPU")
    EntityManager em;


    public Inventory getInventoryOfType(final ProductType coffeebeans) {

        return null;
    }

    public void updateInventory(Inventory inventory) {
        em.merge(inventory);
    }
}
