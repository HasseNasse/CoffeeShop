package net.hassannazar.order.model.event;

import javax.json.bind.JsonbBuilder;

public class InventoryAllocationEvent {
    public long id;
    public int beans;

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }

    public static InventoryAllocationEvent fromJson(final String ser) {
        return JsonbBuilder.create().fromJson(ser, InventoryAllocationEvent.class);
    }
}
