package ch.bbzw.m151.swshop.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producer_sequence")
    @SequenceGenerator(allocationSize = 1, name = "producer_sequence")
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Set<Merch> merch;

    protected Producer() {
    }

    public Producer(final String name, final Set<Merch> merch) {
        this.name = name;
        this.merch = merch;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Merch> getMerch() {
        return merch;
    }

    public void setMerch(final Set<Merch> merch) {
        this.merch = merch;
    }
}
