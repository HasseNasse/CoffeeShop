package io.hreem.coffeeshop.monolith.ordering.boundary.rest;

import io.hreem.coffeeshop.monolith.ordering.controller.internal.commands.OrderCommands;
import io.hreem.coffeeshop.monolith.ordering.domain.readobjects.Order;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hreem.io
 */
@RequestScoped
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Orders", description = "Endpoints allowing to create and handle orders")
public class OrderResource {

    @Context
    UriInfo uriInfo;

    @Inject
    OrderCommands orderCommands;

    @POST
    @Operation(summary = "Create a new Order")
    public Response postOrder(@NotNull @Valid final Order order) {
        final var orderId = this.orderCommands.createOrder(order);
        final var orderURI = this.uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(orderId)).build();
        return Response.created(orderURI).build();
    }
}
