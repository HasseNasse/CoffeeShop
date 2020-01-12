package net.hassannazar.orders.model;

import org.wildfly.common.annotation.NotNull;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class Order implements Serializable {

    public UUID id;
    @NotEmpty(message = "Name of recipient must be present")
    public String orderer;
    @NotEmpty(message = "Coffee type name must be present")
    public String type;
    @NotNull
    public int quantity;
    public OrderStatus status;
    public LocalDateTime created;
    public LocalDateTime modified;
    public String modifier;

}
