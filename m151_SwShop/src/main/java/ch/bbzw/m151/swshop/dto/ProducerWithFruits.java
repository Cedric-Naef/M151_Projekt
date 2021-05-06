package ch.bbzw.m151.swshop.dto;

import java.io.Serializable;
import java.util.List;

public class ProducerWithMerch implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final List<Long> merch;

    public ProducerWithMerch(final String name, final List<Long> merch) {
        this.name = name;
        this.merch = merch;
    }

    public String getName() {
        return name;
    }

    public List<Long> getMerch() {
        return merch;
    }
}
