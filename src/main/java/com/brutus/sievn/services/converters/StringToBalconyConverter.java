package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.Balcony;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBalconyConverter implements Converter<String, Balcony> {
    @Override
    public Balcony convert(String source) {

        Balcony value = Balcony.forRussian(source);

        if (value == null) {

            value = Balcony.forEnglish(source);

            if (value == null) {
                value = Balcony.forArmenian(source);
            }
        }

        return value;
    }
}
