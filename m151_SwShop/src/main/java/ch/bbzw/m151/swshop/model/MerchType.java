package ch.bbzw.m151.swshop.model;

import java.util.Optional;

public enum MerchType {
    WAFFE("Lichtschwert"),
    KLEIDUNG("Robe"),
    PROP("Holocrom"),
    HELM("Darth Vader");

    private final String value;

    MerchType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<MerchType> fromValue(final String value) {
        for (final MerchType merchType : MerchType.values()) {
            if (merchType.getValue().equalsIgnoreCase(value)) {
                return Optional.of(merchType);
            }
        }
        return Optional.empty();
    }
}
