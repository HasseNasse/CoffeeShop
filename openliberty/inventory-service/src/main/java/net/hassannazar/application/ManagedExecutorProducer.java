package net.hassannazar.application;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@ApplicationScoped
public class ManagedExecutorProducer {

    @Produces
    @AppContext
    public ManagedExecutor getManagedExecutor() {
        return ManagedExecutor.builder()
                .propagated(ThreadContext.APPLICATION)
                .build();
    }
}
