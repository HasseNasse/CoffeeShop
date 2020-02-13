package net.hassannazar.inventory.model.event;

import net.hassannazar.inventory.model.CoffeeType;

import javax.json.bind.JsonbBuilder;

public class OrderCreatedEvent {
    public long orderId;
    public int quantity;
    public CoffeeType coffeeType;

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }

    public static OrderCreatedEvent fromJson(final String ser) {
        return JsonbBuilder.create().fromJson(ser, OrderCreatedEvent.class);
    }
}
