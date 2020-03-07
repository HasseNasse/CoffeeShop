package net.hassannazar.order.model.aggregate;

import net.hassannazar.order.model.CoffeeType;
import net.hassannazar.order.model.OrderStatus;

import javax.json.bind.JsonbBuilder;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderAggregate {
    public long orderId;
    @NotEmpty
    public String orderer;

    @NotNull
    @Max(10)
    public int quantity;

    @NotNull
    public CoffeeType coffeeType;

    @Null
    public OrderStatus status;

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }

    public static OrderAggregate fromJson(final String ser) {
        return JsonbBuilder.create().fromJson(ser, OrderAggregate.class);
    }
}
