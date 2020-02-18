package net.hassannazar.inventory.domain;

import java.util.concurrent.CompletionException;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class NotEnoughStockException extends CompletionException {
    public NotEnoughStockException(final String message) {
        super(message);
    }
}
