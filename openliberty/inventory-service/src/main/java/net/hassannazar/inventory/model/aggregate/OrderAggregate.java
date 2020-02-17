package net.hassannazar.inventory.model.aggregate;

import net.hassannazar.inventory.model.CoffeeType;
import net.hassannazar.inventory.model.OrderStatus;

import javax.json.bind.JsonbBuilder;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderAggregate {
    public long orderId;
    public String orderer;
    public int quantity;
    public CoffeeType coffeeType;
    public OrderStatus status;

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }

    public static OrderAggregate fromJson(final String ser) {
        return JsonbBuilder.create().fromJson(ser, OrderAggregate.class);
    }
}
