package ch.bbzw.m151.swshop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Merch implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merch_sequence")
    @SequenceGenerator(allocationSize = 1, name = "merch_sequence")
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String merchName;

    @Column(nullable = false)
    private MerchType merchType;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Price price;

    protected Merch() {
    }

    public Merch(final String merchName, final MerchType merchType, final Price price) {
        this.merchName = merchName;
        this.merchType = merchType;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getMerchName() {
        return merchName;
    }

    public MerchType getMerchType() {
        return merchType;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Merch - Id: %d, Name: %s, Type: %s", id, merchName, merchType.getValue());
    }
}
