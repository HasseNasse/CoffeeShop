package net.hassannazar.inventory.domain;

import net.hassannazar.application.exceptions.NoSuchCoffeeException;
import net.hassannazar.application.exceptions.NotEnoughBeansException;
import net.hassannazar.inventory.model.Order;
import net.hassannazar.inventory.model.OrderStatus;
import net.hassannazar.inventory.repository.BeansRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.UUID;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Dependent
public class BeanAllocatorService {

    @Inject
    BeansRepository beansRepository;

    @Inject
    CoffeeClassifier classifier;


    public void allocateBeansForOrder (Order order) throws NoSuchCoffeeException, NotEnoughBeansException {
        System.out.println("Attempting to allocate beans for order with id: " + order.id);
        final int gramsOfBeans = classifier.classifyCoffee(order.type);
        beansRepository.allocate(gramsOfBeans * order.quantity);
    }
}
