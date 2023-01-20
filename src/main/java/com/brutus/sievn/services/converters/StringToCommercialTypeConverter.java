package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.CommercialType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCommercialTypeConverter implements Converter<String, CommercialType> {

    @Override
    public CommercialType convert(String source) {

        CommercialType value = CommercialType.forRussian(source);

        if (value == null) {

            value = CommercialType.forEnglish(source);

            if (value == null) {
                value = CommercialType.forArmenian(source);
            }
        }

        return value;
    }
}
