package net.hassannazar.inventory.gateway.model;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class OrderCreatedDeserializer extends JsonbDeserializer<OrderCreated> {

    public OrderCreatedDeserializer() {
        super(OrderCreated.class);
    }

}
