package net.hassannazar.inventory.model;

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
    public String type;
    public int quantity;
    public OrderStatus status;
    public String modifier;

}
