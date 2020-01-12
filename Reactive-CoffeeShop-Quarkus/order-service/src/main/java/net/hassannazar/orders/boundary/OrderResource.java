package net.hassannazar.orders.boundary;

import io.smallrye.reactive.messaging.annotations.Channel;
import net.hassannazar.orders.gateway.OrderProducer;
import net.hassannazar.orders.model.Order;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

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
    @Channel("status")
    Publisher<Order> orders;

    @Inject
    OrderProducer service;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Publisher<Order> stream() {
        return orders;
    }

    @POST
    @Override
    @Retry(maxRetries = 4)
    public Response postOrder (@Valid Order order) {
        service.placeOrder(order);
        return Response.ok().build();
    }


}
