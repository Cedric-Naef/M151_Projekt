package ch.bbzw.m151.swshop.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MerchTypeConverter implements AttributeConverter<MerchType, String> {
    @Override
    public String convertToDatabaseColumn(final MerchType merchType) {
        return merchType.getValue();
    }

    @Override
    public MerchType convertToEntityAttribute(final String value) {
        return MerchType.fromValue(value).orElse(null);
    }
}
