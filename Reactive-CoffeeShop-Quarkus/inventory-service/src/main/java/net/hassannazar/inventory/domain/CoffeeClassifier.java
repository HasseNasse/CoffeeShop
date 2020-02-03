package net.hassannazar.inventory.domain;

import javax.enterprise.context.Dependent;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class CoffeeClassifier {

    /**
     * Given a coffee name, we try to decipher how much grams of beans are needed
     * to prepare it.
     * <p>
     * Would IRL call a product-service to fetch the "cost" of a product, in our case
     * the cost is the amount of beans required to fullfill the order.
     *
     * @param type
     * @return
     */
    public int classifyCoffee(final String type) {
        if (type == null) {
            return 0;
        }

        final var formattedType = type.toLowerCase().replace(" ", "-");
        if (formattedType.equals("black") || formattedType.equals("latte")) {
            return 10;
        }
        if (formattedType.equals("americano") || formattedType.equals("espresso")) {
            return 20;
        }
        if (formattedType.equals("double-espresso")) {
            return 40;
        }

        return 0;
    }
}
