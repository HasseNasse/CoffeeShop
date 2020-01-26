package net.hassannazar.orders.repository;

import net.hassannazar.orders.model.OrderEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Purpose:
 * Application
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class OrdersRepository {

    @Inject
    EntityManager entityManager;

    @Transactional
    public OrderEntity save(final OrderEntity entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    public List<OrderEntity> getAll() {
        final var query = entityManager.createNamedQuery("OrderEntity.findAll", OrderEntity.class);
        return query.getResultList();
    }
}
