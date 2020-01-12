package net.hassannazar.orders.boundary;

import net.hassannazar.orders.model.Order;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

public interface IOrderResource {

    Response postOrder(@Valid Order order);

}
