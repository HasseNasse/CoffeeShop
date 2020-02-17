package net.hassannazar.inventory.domain;

import net.hassannazar.inventory.model.CoffeeType;

import javax.enterprise.context.Dependent;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class CoffeeBeansResolver {

    int resolve(final CoffeeType type) {
        return switch (type) {
            case BLACK, LATTE -> 10;
            case ESPRESSO, AMERICANO -> 15;
            case DOUBLE_ESPRESSO -> 30;
        };
    }
}
