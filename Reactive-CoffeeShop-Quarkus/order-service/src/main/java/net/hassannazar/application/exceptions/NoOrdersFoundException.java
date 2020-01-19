package net.hassannazar.application.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class NoOrdersFoundException extends WebApplicationException {
    public NoOrdersFoundException() {
        super("No orders found in the database", Response
                .status(Response.Status.NOT_FOUND)
                .build()
        );
    }
}
