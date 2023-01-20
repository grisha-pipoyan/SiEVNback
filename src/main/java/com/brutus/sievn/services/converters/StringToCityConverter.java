package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.Cities;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCityConverter implements Converter<String,Cities> {

    @Override
    public Cities convert(String source) {

        Cities value = Cities.forRussian(source);

        if (value == null) {

            value = Cities.forEnglish(source);

            if (value == null) {
                value = Cities.forArmenian(source);
            }
        }

        return value;
    }
}
