package net.hassannazar.order.repository;

import net.hassannazar.order.model.entity.OrderEntity;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class OrderRepository {

    @PersistenceContext(unitName = "orders")
    private EntityManager em;

    @Transactional
    public OrderEntity save(final OrderEntity entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }


}
