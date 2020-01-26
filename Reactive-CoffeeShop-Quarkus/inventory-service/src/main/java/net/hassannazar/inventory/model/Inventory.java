package net.hassannazar.inventory.model;

import javax.persistence.*;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Entity
@Table(name = "inventory")
@NamedQuery(name = "InventoryEntity.findByType", query = "select c from Inventory c where type = :type")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private InventoryType type;
    private int stock;

    public Inventory() {
    }

    public long getId() {
        return id;
    }

    public Inventory setId(final long id) {
        this.id = id;
        return this;
    }

    public InventoryType getType() {
        return type;
    }

    public Inventory setType(final InventoryType type) {
        this.type = type;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Inventory setStock(final int stock) {
        this.stock = stock;
        return this;
    }
}
