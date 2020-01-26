package net.hassannazar.order.boundary;

import net.hassannazar.order.domain.OrderService;
import net.hassannazar.order.model.entity.OrderEntity;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Path("orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private OrderService service;

    @GET
    public Response getAllOrders() {
        return Response.ok().build();
    }

    @POST
    public Response createOrder(@Valid final OrderEntity entity) {
        final var uriId = this.service.createOrder(entity);
        final var uriLocation = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(uriId)).build();
        return Response.created(uriLocation).build();
    }
}
