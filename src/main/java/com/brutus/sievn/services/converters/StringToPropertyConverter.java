package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.Property;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPropertyConverter implements Converter<String, Property> {

    @Override
    public Property convert(String source) {

        Property value = Property.forRussian(source);

        if (value == null) {

            value = Property.forEnglish(source);

            if (value == null) {
                value = Property.forArmenian(source);
            }
        }

        return value;
    }

}
