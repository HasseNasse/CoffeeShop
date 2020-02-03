package net.hassannazar.inventory.gateway.model;

import org.wildfly.common.annotation.NotNull;

import javax.validation.constraints.NotEmpty;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderCreated {
    @NotNull
    public long id;
    @NotEmpty
    public String type;
    @NotNull
    public int quantity;
}
