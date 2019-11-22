package net.hassannazar.coffee

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/order")
class OrderResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "hello"
}