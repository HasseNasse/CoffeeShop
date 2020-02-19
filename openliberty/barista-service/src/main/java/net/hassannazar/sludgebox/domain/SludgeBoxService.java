package net.hassannazar.sludgebox.domain;

import net.hassannazar.sludgebox.model.SludgeBox;
import net.hassannazar.sludgebox.repository.SludgeBoxRepository;

import javax.inject.Inject;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class SludgeBoxService {

    @Inject
    SludgeBoxRepository repository;

    /**
     * In case of encountering unreadable messages, we want to
     * store it away and continue processing as to not halt the
     * application.
     *
     * @param payload unreadable input
     */
    public void handleUnreadable(final String payload) {
        // Create a sludge entity
        final var sludge = new SludgeBox();
        sludge.setPayload(payload);

        this.repository.save(sludge);
    }
}
