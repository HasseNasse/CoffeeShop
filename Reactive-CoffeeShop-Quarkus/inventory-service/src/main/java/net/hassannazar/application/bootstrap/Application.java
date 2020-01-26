package net.hassannazar.application.bootstrap;

import io.quarkus.runtime.StartupEvent;
import net.hassannazar.inventory.domain.BeanAllocatorService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class Application {

    @Inject
    BeanAllocatorService allocatorService;

    void onStart(@Observes final StartupEvent startupEvent) {
        allocatorService.restockBeansInInventory(2000);
    }
}
