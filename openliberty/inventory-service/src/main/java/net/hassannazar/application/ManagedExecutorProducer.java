package net.hassannazar.application;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

/**
 * Purpose:
 * Produces an MP Context propagation managed-executor which allowes
 * for simplified concurrency handling.
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

    void handleDisposal(@Disposes @AppContext final ManagedExecutor executor) {
        executor.shutdownNow();
    }
}
