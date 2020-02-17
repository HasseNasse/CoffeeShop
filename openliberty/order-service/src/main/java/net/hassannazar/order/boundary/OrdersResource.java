package net.hassannazar.order.boundary;

import net.hassannazar.order.domain.OrderService;
import net.hassannazar.order.model.aggregate.OrderAggregate;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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

    @Inject
    @ConfigProperty(name = "application.use.orchestration.saga")
    private boolean useOrchestration;

    @Inject
    @ConfigProperty(name = "application.use.choreography.saga")
    private boolean useChoreography;


    @GET
    public Response getAllOrders() {
        final var orders = this.service.getAllOrders();
        return Response.ok(orders).build();
    }

    @POST
    public Response createOrder(@Valid final OrderAggregate order) {
        var uriId = -1L;
        if (this.useChoreography) {
            uriId = this.service.createOrder(order);
        }
        if (this.useOrchestration) {
            throw new NotImplementedException("Not yet implemented");
        }
        final var uriLocation = this.uriInfo.getAbsolutePathBuilder().path(Long.toString(uriId)).build();
        return Response.created(uriLocation).build();
    }
}
