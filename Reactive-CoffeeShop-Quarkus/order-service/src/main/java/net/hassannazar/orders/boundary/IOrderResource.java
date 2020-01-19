package net.hassannazar.orders.boundary;

import net.hassannazar.orders.model.read.Order;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

public interface IOrderResource {

    @GET
    Response getOrders();

    Response postOrder(@Valid Order order);

}
