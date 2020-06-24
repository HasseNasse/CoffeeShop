package io.hreem.coffeeshop.monolith.ordering.domain.readobjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class Order {
    @Null
    public Long id;
    @NotNull(message = "No product code provided")
    public Long productCode;
    @Min(message = "A minimum order must include one in quantity", value = 1)
    public int quantity;
}
