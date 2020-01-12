package net.hassannazar.inventory.repository;

import net.hassannazar.application.exceptions.NotEnoughBeansException;

import javax.enterprise.context.ApplicationScoped;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class BeansRepository {
    private int beansInGram = 2000;

    public void allocate (int gramsToAllocate) throws NotEnoughBeansException {
        if (beansInGram - gramsToAllocate < 0)
            throw new NotEnoughBeansException();

        // Allocate necessary beans for the order
        beansInGram = beansInGram - gramsToAllocate;
        System.out.println("Successfully allocated " + gramsToAllocate + " grams! " + beansInGram + " left in stock!");
    }
}
