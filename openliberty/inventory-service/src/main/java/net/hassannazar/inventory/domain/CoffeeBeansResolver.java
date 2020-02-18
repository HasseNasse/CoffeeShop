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
        switch (type) {
            case BLACK:
            case LATTE:
                return 10;
            case ESPRESSO:
            case AMERICANO:
                return 15;
            case DOUBLE_ESPRESSO:
                return 30;
            default:
                return -1;
        }
    }
}
