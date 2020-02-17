package net.hassannazar.inventory.domain;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class NotEnoughStockException extends Throwable {
    public NotEnoughStockException(final String message) {
        super(message);
    }
}
