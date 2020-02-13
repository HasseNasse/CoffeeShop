package net.hassannazar.order.repository;

import net.hassannazar.order.model.Order;

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

    public Order save(final Order entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }

    public List<Order> getAllOrders() {
        final var query = this.em.createNamedQuery("Orders.getAll", Order.class);
        return query.getResultList();
    }

    public Order findById(final long id) {
        final var orders = this.em.find(Order.class, id);
        if (orders == null) {
            throw new IllegalStateException();
        }
        return orders;
    }

    public void update(final Order order) {
        this.em.merge(order);
    }
}
