package net.hassannazar.inventory.model.serde;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;
import net.hassannazar.inventory.model.Order;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderDeserializer extends JsonbDeserializer<Order> {
    public OrderDeserializer() {
        super(Order.class);
    }
}

