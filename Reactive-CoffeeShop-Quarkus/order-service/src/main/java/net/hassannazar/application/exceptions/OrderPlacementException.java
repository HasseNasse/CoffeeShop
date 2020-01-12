package net.hassannazar.application.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderPlacementException extends WebApplicationException {
    public OrderPlacementException () {
        super(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
    }
}
