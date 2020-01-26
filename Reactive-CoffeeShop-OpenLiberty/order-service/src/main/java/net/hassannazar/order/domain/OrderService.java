package net.hassannazar.order.domain;

import net.hassannazar.order.model.entity.OrderEntity;
import net.hassannazar.order.repository.OrderRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@RequestScoped
public class OrderService {

    @Inject
    private
    OrderRepository repository;

    public long createOrder(final OrderEntity entity) {
        final var saved = this.repository.save(entity);
        return saved.getId();
    }
}
