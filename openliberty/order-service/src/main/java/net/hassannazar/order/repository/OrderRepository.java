package net.hassannazar.order.repository;

import net.hassannazar.order.model.OrderEntity;

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

    public OrderEntity save(final OrderEntity entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }

    public List<OrderEntity> getAllOrders() {
        final var query = this.em.createNamedQuery(OrderEntity.GETALL, OrderEntity.class);
        return query.getResultList();
    }

    public OrderEntity findById(final long id) {
        final var orders = this.em.find(OrderEntity.class, id);
        if (orders == null) {
            throw new IllegalStateException();
        }
        return orders;
    }

    public void update(final OrderEntity orderEntity) {
        this.em.merge(orderEntity);
    }
}
