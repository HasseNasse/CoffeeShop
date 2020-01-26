package net.hassannazar.orders.boundary;

import net.hassannazar.orders.domain.OrderManagementService;
import net.hassannazar.orders.model.read.Order;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource implements IOrderResource {

    @Context
    UriInfo uriInfo;

    @Inject
    OrderManagementService orderManagementService;

    @GET
    @Override
    public Response getOrders() {
        return Response.ok(orderManagementService.getAllOrders()).build();
    }

    @POST
    @Override
    @Retry(maxRetries = 4)
    public Response postOrder(final Order order) {
        final var id = orderManagementService.placeOrder(order);
        final var uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(id)).build();
        return Response.created(uri).build();
    }

}
