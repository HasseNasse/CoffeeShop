package net.hassannazar.inventory.model;

import javax.persistence.*;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Entity
@NamedQuery(name = Inventory.GET_BY_INVENTORY_TYPE, query = "SELECT d FROM Inventory d WHERE d.type LIKE :type")
public class Inventory {
    public static final String GET_BY_INVENTORY_TYPE = "Inventory.getByInventoryType";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    private int quantity;

    public Inventory() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public ProductType getType() {
        return this.type;
    }

    public void setType(final ProductType type) {
        this.type = type;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}
