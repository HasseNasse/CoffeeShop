package net.hassannazar.order.repository;

import net.hassannazar.order.model.Orders;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class OrderRepository {

    @PersistenceContext(unitName = "ordersPU")
    private EntityManager em;

    public Orders save(final Orders entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }

    public List<Orders> getAllOrders() {
        final var query = this.em.createNamedQuery("Orders.getAll", Orders.class);
        return query.getResultList();
    }

    public Orders findById(final long id) {
        final var orders = this.em.find(Orders.class, id);
        if (orders == null) {
            throw new IllegalStateException();
        }
        return orders;
    }

    public void update(final Orders order) {
        this.em.merge(order);
    }
}
