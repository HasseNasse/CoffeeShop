package net.hassannazar.orders.boundary;

import net.hassannazar.orders.domain.OrderManagementService;
import net.hassannazar.orders.model.read.Order;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource implements IOrderResource {

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
    public Response postOrder(@Valid final Order order) {
        orderManagementService.placeOrder(order);
        return Response.ok().build();
    }

}
