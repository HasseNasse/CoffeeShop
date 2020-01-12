package net.hassannazar.inventory.domain;

import net.hassannazar.application.exceptions.NoSuchCoffeeException;

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
     *
     * @param type
     * @return
     */
    public int classifyCoffee (String type) throws NoSuchCoffeeException {
        final var formattedType = type.toLowerCase().replace(" ", "-");
        if( formattedType.equals("black") || formattedType.equals("latte") )
            return 10;
        if( formattedType.equals("americano") || formattedType.equals("espresso") )
            return 20;
        if( formattedType.equals("double-espresso"))
            return 40;

        // In case of no match
        throw new NoSuchCoffeeException();
    }
}
