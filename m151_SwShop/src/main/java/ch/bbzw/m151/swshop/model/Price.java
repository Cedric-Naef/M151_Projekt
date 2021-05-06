package ch.bbzw.m151.swshop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Price implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_sequence")
    @SequenceGenerator(allocationSize = 1, name = "price_sequence")
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private int discount;

    protected Price() {
    }

    public Price(final float amount, final int discount) {
        this.amount = amount;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public int getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return String.format("Price - Id: %d, Amount: %f, Discount: %d", id, amount, discount);
    }
}
