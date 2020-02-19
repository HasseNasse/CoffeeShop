package net.hassannazar.sludgebox.repository;

import net.hassannazar.sludgebox.model.SludgeBox;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@RequestScoped
public class SludgeBoxRepository {

    @PersistenceContext(unitName = "inventoryPU")
    EntityManager em;

    @Transactional
    public void save(final SludgeBox sludgeBox) {
        this.em.persist(sludgeBox);
    }
}
