package net.hassannazar.order.model.event;

import javax.json.bind.JsonbBuilder;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class StockEmptyEvent {
    public long id;

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }

    public static StockEmptyEvent fromJson(final String ser) {
        return JsonbBuilder.create().fromJson(ser, StockEmptyEvent.class);
    }

}
